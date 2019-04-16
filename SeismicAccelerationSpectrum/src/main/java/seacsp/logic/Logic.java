package seacsp.logic;

import java.io.File;
import seacsp.calculations.Frequencies;
import seacsp.data.DataFile;
import seacsp.data.DataFiles;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import java.util.*;
import javafx.util.Pair;

public class Logic {
    final private DataFiles dataFiles;
    private Phii phii;
    private Frequencies frequencies;
    final private LogList logList;

    public Logic() {
        this.logList = new LogList();
        this.dataFiles = new DataFiles(this.logList);
        this.phii = new Phii(0.05);
        this.frequencies = new Frequencies();
        this.frequencies.asceDivision();        
    }
    
    public void setPhiiValue(double newPhii) {
        this.phii.setPhii(newPhii);
    }       

    public LogList getLogList() {
        return logList;
    }
    
    public void addLogMessage(String message) {
        this.logList.addLog(message);
    }
    
    public void equalDivisionFrequence(double start, double end, double divisionHz) {
        this.frequencies.equalDivision(start, end, divisionHz);
    }
    
    public DataFile addFileWhenSelectFileButtonPressed(File file) {
        try {
            DataFile dataFile = this.dataFiles.addFile(file);
            logList.addLog("File " + file.getName() + " added to list.");            
            return dataFile;            
        } catch (Exception e) {
            logList.addLog(e.toString());
            return null;
        }        
    }
    
    public ArrayList<Pair<Double, Double>> getXYValuesForChart() {
        this.dataFiles.calculate(this.frequencies, this.phii);
        ArrayList<Spectrum> spectrumList = new ArrayList<Spectrum>();
        this.dataFiles.getSpectrumList(spectrumList);                
        ArrayList<Pair<Double, Double>> pairs = new ArrayList<>();
        for (int i = 0; i < this.frequencies.numberOfFrequencies(); i++) {
            double max = 0;
            for (Spectrum spectrum : spectrumList) {
                if (spectrum.getAccWithFrequency(i) > max) {
                    max = spectrum.getAccWithFrequency(i);
                }
            }
            pairs.add(new Pair<>(this.frequencies.getFrequency(i), max));
        }
        return pairs;
    }
    
    public ArrayList<ArrayList<Pair<Double, Double>>> getTimehistoryLists() {
        ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists = new ArrayList<>();
        this.dataFiles.getTimehistoryLists(timehistoryLists);
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
