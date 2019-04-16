package seacsp.data;

import java.io.File;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.logic.LogList;

public class DataFiles {
    final private ArrayList<DataFile> dataFileList;
    private double currentPhii;
    final private ArrayList<Double> currentFrequencies;
    final private LogList loglist;

    public DataFiles(LogList setLoglist) {
        this.dataFileList = new ArrayList<>();
        this.currentPhii = -0.1;
        this.currentFrequencies = new ArrayList<>();
        this.loglist = setLoglist;
    }
    
    public void getTimehistoryLists(ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists) {
        for (DataFile dataFile : this.dataFileList) {
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
            for (DataFile dataFile : this.dataFileList) {
                dataFile.setCalculatedFalse();
            }
        }
        loglist.addLog("*** Calculation started " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        for (DataFile dataFile : this.dataFileList) {
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
        for (DataFile dataFile : this.dataFileList) {
            dataFile.addSpectrumsToList(spectrumList);
        }
    }
    
    public DataFile addFile(File file) throws Exception {
        if (fileAlreadyAdded(file)) {
            throw new IllegalArgumentException("File " + file.getName() + " already added.");
        }
        ReadTxtFile readTxtFile = new ReadTxtFile(file);
        DataFile newFile = readTxtFile.readTxtFile();
        this.dataFileList.add(newFile);
        return newFile;
    }
    
    public boolean fileAlreadyAdded(File file) {
        String newFileName = file.getName();
        for (DataFile dataFile : this.dataFileList) {
            if (newFileName.equals(dataFile.getFileName())) {
                return true;
            }
        }        
        return false;
    }
}
