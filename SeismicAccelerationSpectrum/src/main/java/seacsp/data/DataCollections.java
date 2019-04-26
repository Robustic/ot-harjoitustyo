package seacsp.data;

import java.io.File;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.db.TimehistoryDao;
import seacsp.db.DataCollectionDao;
import seacsp.logic.LogList;
import seacsp.file.ReadFile;
import seacsp.file.ReadTxtFile;
import java.sql.SQLException;

public class DataCollections {
    final private ArrayList<DataCollection> dataCollectionList;
    private double currentPhii;
    final private ArrayList<Double> currentFrequencies;
    final private LogList loglist;
    final private ReadFile readFile;
    final private ReadTxtFile readTxtFile;
    final private TimehistoryDao timehistoryDao;
    final private DataCollectionDao dataCollectionDao;

    public DataCollections(LogList setLoglist, ReadFile readFile) {
        this.dataCollectionList = new ArrayList<>();
        this.currentPhii = -0.1;
        this.currentFrequencies = new ArrayList<>();
        this.loglist = setLoglist;
        this.readFile = readFile;
        this.readTxtFile = new ReadTxtFile(this.readFile);
        this.timehistoryDao = new TimehistoryDao();
        this.dataCollectionDao = new DataCollectionDao();
    }
    
    public void saveTimehistoriesToDB(File dbFile) {
        this.dataCollectionDao.setTimehistoryDao(this.timehistoryDao);
        this.dataCollectionDao.setDbFile(dbFile);
        try { 
            for (int i = 0; i < this.dataCollectionList.size(); i++) {
                loglist.addLog("Writing file " + this.dataCollectionList.get(i).getName() + " to the database started. Just wait a moment...");
                this.dataCollectionDao.create(this.dataCollectionList.get(i));
                loglist.addLog("Writing file " + this.dataCollectionList.get(i).getName() + " finished.");
            }
        } catch (SQLException e) {
            loglist.addLog("Writing to the database caused error.");
        }     
    }
    
    public void getTimehistoryLists(ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists) {
        for (DataCollection dataFile : this.dataCollectionList) {
            dataFile.getTimehistoryLists(timehistoryLists);
        }
    }

    public boolean neededToRecalculate(Frequencies frequencies, Phii phii) {
        boolean returnValue = false;
        if (!sameDoubles(this.currentPhii, phii.getPhii())) {
            this.currentPhii = phii.getPhii();
            returnValue = true;
        }
        ArrayList<Double> frequenceList = frequencies.getFrequenceList();
        if (this.currentFrequencies.size() != frequencies.numberOfFrequencies()) {
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
    
    public boolean sameDoubles(double d1, double d2) {
        Double double1 = new Double(d1);
        Double double2 = new Double(d2);
        if (double1.compareTo(double2) == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public void copyFrequencies(ArrayList<Double> frequenceList) {
        this.currentFrequencies.clear();
        for (int i = 0; i < frequenceList.size(); i++) {
            this.currentFrequencies.add(frequenceList.get(i));
        }
    }
    
    public void calculate(Frequencies frequencies, Phii phii) {        
        if (neededToRecalculate(frequencies, phii) == true) {
            for (DataCollection dataFile : this.dataCollectionList) {
                dataFile.setCalculatedFalse();
            }
        }
        loglist.addLog("*** Calculation started " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        for (DataCollection dataFile : this.dataCollectionList) {
            if (dataFile.isCalculated() == false) {
                dataFile.calculate(frequencies, phii);
                loglist.addLog("File " + dataFile.getFileName() + " calculated.");
                
            } else {
                loglist.addLog("File " + dataFile.getFileName() + " already calculated.  No need to recalculate.");
            }
        }
        loglist.addLog("*** Calculation finished " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
    
    public void getSpectrumList(ArrayList<Spectrum> spectrumList) {
        for (DataCollection dataFile : this.dataCollectionList) {
            dataFile.addSpectrumsToList(spectrumList);
        }
    }
    
    public DataCollection addFile(File file) throws Exception {
        if (fileAlreadyAdded(file)) {
            throw new IllegalArgumentException("File " + file.getName() + " already added.");
        }
        DataCollection newFile = this.readTxtFile.readTxtFile(file);
        this.dataCollectionList.add(newFile);
        return newFile;
    }
    
    public boolean fileAlreadyAdded(File file) {
        String newFileName = file.getName();
        for (DataCollection dataFile : this.dataCollectionList) {
            if (newFileName.equals(dataFile.getFileName())) {
                return true;
            }
        }        
        return false;
    }
}
