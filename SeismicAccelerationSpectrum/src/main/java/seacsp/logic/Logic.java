package seacsp.logic;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.calculations.Timehistory;
import seacsp.data.DataCollection;
import seacsp.data.DataCollections;
import seacsp.file.ReadFile;
import seacsp.db.InitializeDatabase;
import seacsp.db.TimehistoryDao;


public class Logic {
    final private DataCollections dataFiles;
    private Phii phii;
    private Frequencies frequencies;
    final private LogList logList;
    final private ReadFile readFile;
    private File dbFile;
    private TimehistoryDao timehistoryDao;
    private InitializeDatabase initializeDatabase;

    public Logic() {
        this.logList = new LogList();
        this.readFile = new ReadFile();
        this.dataFiles = new DataCollections(this.logList, this.readFile);
        this.phii = new Phii(0.05);
        this.frequencies = new Frequencies();
        this.frequencies.asceDivision();
        this.timehistoryDao = new TimehistoryDao();
        this.initializeDatabase = new InitializeDatabase();
    }

    public void setDbFile(File dbFile) {
        this.dbFile = dbFile;
    }
    
    public String initialize(String filename) {
        if (filename.equals("-1")) {
            this.logList.addLog("New database not initialized. Filename was empty.");
            return "";
        } else {           
            String dbname = filename + ".db";
            if (this.readFile.checkThatFileExists(new File(dbname))) {
                this.logList.addLog("New database not initialized. Filename already exists.");
                return "";
            } else {
                initializeDb(dbname);
                return dbname;
            }
        }
    }
    
    public void initializeDb(String dbname) {
        try {
            initializeDatabase.initializeDatabase(dbname);
            this.logList.addLog("New database " + dbname + " initialized!");
            this.dbFile = new File(dbname);
            this.dbFile = new File(this.dbFile.getAbsolutePath());
        } catch (SQLException e) {
            this.logList.addLog("There was error. New database was not initialized.");
        }        
    }
    
    public void saveTimehistoriesToDB() {
        timehistoryDao.setDbFile(this.dbFile);
        timehistoryDao.setCollectionId(123);
        try { 
            ArrayList<Double> lista = new ArrayList<>();
            lista.add(1.1);
            lista.add(2.3);
            lista.add(3.4);
            lista.add(4.5);
            lista.add(5.6);
            
            timehistoryDao.create(new Timehistory(lista, 0.1, "OmaNimi"));
            timehistoryDao.create(new Timehistory(lista, 12.1, "Faf"));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }     
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
    
    public DataCollection addFileWhenSelectFileButtonPressed(File file) {
        try {
            DataCollection dataFile = this.dataFiles.addFile(file);
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
