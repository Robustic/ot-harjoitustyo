package seacsp.logic;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.data.DataCollection;
import seacsp.data.DataCollections;
import seacsp.db.InitializeDatabase;
import seacsp.file.ReadFile;

/**
 * Class to describe logic for Seismic Acceleration Spectrum application.
 */
public class Logic {
    final private DataCollections dataCollections;
    final private Phii phii;
    final private Frequencies frequencies;
    final private LogList logList;
    final private ReadFile readFile;
    private File dbFile;    
    final private InitializeDatabase initializeDatabase;

    /**
     * Constructor.
     */
    public Logic() {
        this.logList = new LogList();
        this.readFile = new ReadFile();
        this.dataCollections = new DataCollections(this.logList, this.readFile);
        this.phii = new Phii(0.05);
        this.frequencies = new Frequencies();
        this.frequencies.setEqualDivision(0.1, 5.0005, 0.01);        
        this.initializeDatabase = new InitializeDatabase();
    }

    /**
     * Method to set database file.
     *
     * @param   dbFile   database file
     */
    public void setDatabaseFile(File dbFile) {
        this.dbFile = dbFile;
    }
    
    /**
     * Method to read data from SQL-database.
     *
     * @return DataCollections as a list read from the database
     */
    public ArrayList<DataCollection> readDataCollectionsFromDatabase() {
        if (!this.dataCollections.tablesDatacollectionAndTimehistoryExist(this.dbFile)) {
            this.logList.addNewLogMessage("Needed tables are missing from database. Select other database file or create new database.");
            return null;
        } else {
            return this.dataCollections.readDataCollectionsFromDatabase(this.dbFile);
        }
    }
    
    /**
     * Method to create new SQL-database.
     * 
     * @param   newDatabaseName   new database file name without file name extension
     *
     * @return database file name with file name extension
     */
    public String newDatabaseFile(String newDatabaseName) {
        if (newDatabaseName.equals("-1")) {
            this.logList.addNewLogMessage("New database not initialized. Filename was empty.");
            return "";
        } else {           
            String newDatabaseNameWithExtension = newDatabaseName + ".db";
            if (this.readFile.checkIfFileExists(new File(newDatabaseNameWithExtension))) {
                this.logList.addNewLogMessage("New database not initialized. Filename already exists.");
                return "";
            } else {
                initializeDatabaseFile(newDatabaseNameWithExtension);
                return newDatabaseNameWithExtension;
            }
        }
    }
    
    /**
     * Method to create new SQL-database.
     * 
     * @param   dbName   new database file name with file name extension
     */
    public void initializeDatabaseFile(String dbName) {
        try {
            initializeDatabase.initializeDatabaseFile(dbName);
            this.logList.addNewLogMessage("New database " + dbName + " initialized!");
            this.dbFile = new File(dbName);
            this.dbFile = new File(this.dbFile.getAbsolutePath());
        } catch (SQLException e) {
            this.logList.addNewLogMessage("There was error. New database was not initialized.");
        }        
    }
    
    /**
     * Method to create Timehistory data to SQL-database.
     */
    public void saveDataCollectionsToDatabase() {
        if (this.dbFile == null || this.dbFile.getName().equals("")) {
            this.logList.addNewLogMessage("Database not selected.");
        } else if (!this.dataCollections.tablesDatacollectionAndTimehistoryExist(this.dbFile)) {
            this.logList.addNewLogMessage("Needed tables are missing from database. Select other database file or create new database.");
        } else {
            dataCollections.saveDataCollectionsToDatabase(this.dbFile);
        }
    }
    
    /**
     * Method to set phii value.
     *
     * @param   newPhii   phii value
     */
    public void setPhiiValue(double newPhii) {
        this.phii.setPhii(newPhii);
    }       

    /**
     * Method to get LogList.
     *
     * @return LogList
     */
    public LogList getLogList() {
        return logList;
    }
    
    /**
     * Method to set equal division for spectrum frequencies to calcualate.
     *
     * @param   start   start point [Hz] of the division
     *
     * @param   end   end point [Hz] of the division
     * 
     * @param   divisionHz   division as [Hz]
     */
    public void setEqualDivisionFrequence(double start, double end, double divisionHz) {
        this.frequencies.setEqualDivision(start, end, divisionHz);
    }
    
    /**
     * Method imports text file content as new data collection if not already exists.
     *
     * @param   file   text file to import
     * 
     * @return created DataCollection
     */
    public DataCollection addNewDataCollectionFromTextFile(File file) {
        try {
            DataCollection dataFile = this.dataCollections.addDataCollectionImportedFromTextFile(file);
            logList.addNewLogMessage("File " + file.getName() + " added to list.");            
            return dataFile;            
        } catch (Exception e) {
            logList.addNewLogMessage(e.toString());
            return null;
        }        
    }
    
    /**
     * Method returns frequency - acceleration pairs as list.
     * 
     * @return frequency - acceleration pairs as list
     */
    public ArrayList<Pair<Double, Double>> getSpectrumXYValues() {
        this.dataCollections.calculate(this.frequencies, this.phii);
        ArrayList<Spectrum> spectrumList = new ArrayList<Spectrum>();
        this.dataCollections.addSpectrumsToList(spectrumList);                
        ArrayList<Pair<Double, Double>> pairs = new ArrayList<>();
        for (int i = 0; i < this.frequencies.numberOfFrequenciesInTheList(); i++) {
            double max = 0;
            for (Spectrum spectrum : spectrumList) {
                if (spectrum.getAccelerationInTheIndex(i) > max) {
                    max = spectrum.getAccelerationInTheIndex(i);
                }
            }
            pairs.add(new Pair<>(this.frequencies.getFrequencyInTheIndex(i), max));
        }
        return pairs;
    }
    
    /**
     * Method returns time - acceleration pair lists as list.
     * 
     * @return time - acceleration pair lists as list
     */
    public ArrayList<ArrayList<Pair<Double, Double>>> getTimehistoryXYValueLists() {
        ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists = new ArrayList<>();
        this.dataCollections.getTimehistoriesAsTimeAndAccelerationListPairs(timehistoryLists);
        ArrayList<ArrayList<Pair<Double, Double>>> timehistoryXYValuesForChart = new ArrayList<>();
        for (Pair<ArrayList<Double>, ArrayList<Double>> pairs : timehistoryLists) {
            ArrayList<Pair<Double, Double>> pairList = new ArrayList<>(pairs.getKey().size());
            for (int i = 0; i < pairs.getKey().size(); i++) {
                pairList.add(new Pair<>(pairs.getKey().get(i), pairs.getValue().get(i)));
            }
            timehistoryXYValuesForChart.add(pairList);
        }
        return timehistoryXYValuesForChart;
    }
}
