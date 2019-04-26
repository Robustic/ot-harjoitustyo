package seacsp.logic;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.calculations.Timehistory;
import seacsp.data.DataFile;
import seacsp.data.DataFiles;
import seacsp.db.ReadDatabase;
import seacsp.db.InitializeDatabase;
import seacsp.db.TimehistoryDao;


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
    
    public void initialize() {
        InitializeDatabase initializeDatabase = new InitializeDatabase();
        initializeDatabase.initializeDatabase();
        System.out.println("Initialized!");             
    }
    
    public void readHeadersFromSQL() {
        System.out.println("readHeadersFromSQL()");
        
        TimehistoryDao timehistoryDao = new TimehistoryDao();
        try { 
            timehistoryDao.create(new Timehistory(0.1, "OmaNimi"));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }        
//        try { 
//            System.out.println("Nimi: " + timehistoryDao.read(1).getName() + ", Luku: " + timehistoryDao.read(1).getDeltaT());
//        } catch (SQLException e) {
//            System.out.println(e.toString());
//        }        
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
