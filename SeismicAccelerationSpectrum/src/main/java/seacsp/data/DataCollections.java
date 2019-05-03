package seacsp.data;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.db.DataCollectionDao;
import seacsp.db.TimehistoryDao;
import seacsp.file.ReadFile;
import seacsp.file.ReadTextFile;
import seacsp.logic.LogList;

/**
 * Class to collect several data collections under one collection.
 */
public class DataCollections {
    final private ArrayList<DataCollection> dataCollectionList;
    private double currentPhii;
    final private ArrayList<Double> currentFrequencies;
    final private LogList loglist;
    final private ReadFile readFile;
    final private ReadTextFile readTxtFile;
    final private TimehistoryDao timehistoryDao;
    final private DataCollectionDao dataCollectionDao;

    /**
     * Constructor.
     *
     * @param   setLoglist   log list where messages to the user are written
     *
     * @param   readFile   readFile -object to read files
     */
    public DataCollections(LogList setLoglist, ReadFile readFile) {
        this.dataCollectionList = new ArrayList<>();
        this.currentPhii = -0.1;
        this.currentFrequencies = new ArrayList<>();
        this.loglist = setLoglist;
        this.readFile = readFile;
        this.readTxtFile = new ReadTextFile(this.readFile);
        this.timehistoryDao = new TimehistoryDao();
        this.dataCollectionDao = new DataCollectionDao();
    }
    
    /**
     * Method to read data from SQL-database.
     *
     * @param   dbFile   database file to read
     *
     * @return DataCollections as a list read from the database
     */
    public ArrayList<DataCollection> readDataCollectionsFromDatabase(File dbFile) {
        this.dataCollectionDao.setTimehistoryDao(this.timehistoryDao);
        this.dataCollectionDao.setDatabaseFile(dbFile);
        ArrayList<String> dataCollectionNames = new ArrayList<>();
        for (DataCollection dataCollection : this.dataCollectionList) {
            dataCollectionNames.add(dataCollection.getName());
        }
        ArrayList<DataCollection> dataCollectionsFromDatabase = new ArrayList<>();
        try {
            dataCollectionsFromDatabase = this.dataCollectionDao.listDataCollectionsWithNameNotExistInTheList(dataCollectionNames);
        } catch (SQLException e) {
            loglist.addNewLogMessage("Reading from database caused error.");
        } 
        for (int i = 0; i < dataCollectionsFromDatabase.size(); i++) {
            this.dataCollectionList.add(dataCollectionsFromDatabase.get(i));
            loglist.addNewLogMessage("Collection " + dataCollectionsFromDatabase.get(i).getName() + " readed from database.");
        }
        loglist.addNewLogMessage("Reading from database " + dbFile.getName() + " finished.");
        return dataCollectionsFromDatabase;
    }
    
    /**
     * Method to save data to the SQL-database.
     *
     * @param   dbFile   database file where to save data
     */
    public void saveDataCollectionsToDatabase(File dbFile) {
        this.dataCollectionDao.setTimehistoryDao(this.timehistoryDao);
        this.dataCollectionDao.setDatabaseFile(dbFile);
        try { 
            for (int i = 0; i < this.dataCollectionList.size(); i++) {                
                if (this.dataCollectionDao.dataCollectionWithGivenNameExist(this.dataCollectionList.get(i).getName())) {
                    loglist.addNewLogMessage("File " + this.dataCollectionList.get(i).getName() + " already exist in the DB.");                    
                } else {
                    loglist.addNewLogMessage("Writing file " + this.dataCollectionList.get(i).getName() + " to the database started. Just wait a moment...");
                    this.dataCollectionDao.create(this.dataCollectionList.get(i));
                    loglist.addNewLogMessage("Writing file " + this.dataCollectionList.get(i).getName() + " to the database finished.");
                }
                
            }
        } catch (SQLException e) {
            loglist.addNewLogMessage("Writing to the database caused error.");
        }     
    }
    
    /**
     * Method to collect timeline and time history pairs to the same list.
     *
     * @param   timehistoryLists   list as input where data is added
     */
    public void getTimehistoriesAsTimeAndAccelerationListPairs(ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists) {
        for (DataCollection dataCollection : this.dataCollectionList) {
            dataCollection.getTimehistoriesAsTimeAndAccelerationListPairs(timehistoryLists);
        }
    }

    /**
     * Method to check if there is a need for the recalculation.
     *
     * @param   frequencies   frequency list capsulated inside object
     * 
     * @param   phii   damping value capsulated inside object
     * 
     * @return returns true if there is need for the recalculation
     */
    public boolean isItNeededToRecalculate(Frequencies frequencies, Phii phii) {
        boolean returnValue = false;
        if (!sameDoubles(this.currentPhii, phii.getPhii())) {
            this.currentPhii = phii.getPhii();
            returnValue = true;
        }
        ArrayList<Double> frequenceList = frequencies.getFrequenceList();
        if (this.currentFrequencies.size() != frequencies.numberOfFrequenciesInTheList()) {
            copyFrequencies(frequenceList);
            returnValue = true;
        }
        for (int i = 0; i < frequenceList.size(); i++) {
            if (!sameDoubles(this.currentFrequencies.get(i), frequenceList.get(i))) {
                copyFrequencies(frequenceList);
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }
    
    /**
     * Method checks if the input double values are same.
     *
     * @param   d1   input value 1
     *
     * @param   d2   input value 2
     * 
     * @return true when input double values are same
     */
    public boolean sameDoubles(double d1, double d2) {
        Double double1 = new Double(d1);
        Double double2 = new Double(d2);
        if (double1.compareTo(double2) == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Method copies frequency values to the object.
     *
     * @param   frequenceList   frequencies as a list
     */
    public void copyFrequencies(ArrayList<Double> frequenceList) {
        this.currentFrequencies.clear();
        for (int i = 0; i < frequenceList.size(); i++) {
            this.currentFrequencies.add(frequenceList.get(i));
        }
    }
    
    /**
     * Method to calculate spectrums for the each data collection.
     *
     * @param   frequencies   frequency list capsulated inside object
     * 
     * @param   phii   damping value capsulated inside object
     */
    public void calculate(Frequencies frequencies, Phii phii) {        
        if (isItNeededToRecalculate(frequencies, phii) == true) {
            for (DataCollection dataFile : this.dataCollectionList) {
                dataFile.setCalculatedFalse();
            }
        }
        loglist.addNewLogMessage("*** Calculation started " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        for (DataCollection dataCollection : this.dataCollectionList) {
            if (dataCollection.isCalculated() == false) {
                dataCollection.calculate(frequencies, phii);
                loglist.addNewLogMessage("File " + dataCollection.getName() + " calculated.");
                
            } else {
                loglist.addNewLogMessage("File " + dataCollection.getName() + " already calculated.  No need to recalculate.");
            }
        }
        loglist.addNewLogMessage("*** Calculation finished " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
    
    /**
     * Method to collect spectrums to the list.
     * 
     * @param   spectrumList   list as input where collected spectrums are added
     */
    public void addSpectrumsToList(ArrayList<Spectrum> spectrumList) {
        for (DataCollection dataCollection : this.dataCollectionList) {
            dataCollection.addSpectrumsToList(spectrumList);
        }
    }
    
    /**
     * Method to add data in the text file as collection to the data collections.
     * 
     * @throws Exception  If data collection with given name already dataCollectionWithGivenNameExist
     * 
     * @param   file   text file which data will be added as collection to the data collections
     * 
     * @return DataCollection composed from text file content
     */
    public DataCollection addDataCollectionImportedFromFile(File file) throws Exception {
        if (checkIfDataCollectionWithGivenNameAlreadyExist(file)) {
            throw new IllegalArgumentException("File " + file.getName() + " already added.");
        }
        DataCollection newFile = this.readTxtFile.readTextFileToDataCollection(file);
        this.dataCollectionList.add(newFile);
        return newFile;
    }
    
    /**
     * Method to check if there already is data collection with same name as text file.
     * 
     * @param   file   text file
     * 
     * @return true if there already is data collection with same name
     */
    public boolean checkIfDataCollectionWithGivenNameAlreadyExist(File file) {
        String newFileName = file.getName();
        for (DataCollection dataFile : this.dataCollectionList) {
            if (newFileName.equals(dataFile.getName())) {
                return true;
            }
        }        
        return false;
    }
}
