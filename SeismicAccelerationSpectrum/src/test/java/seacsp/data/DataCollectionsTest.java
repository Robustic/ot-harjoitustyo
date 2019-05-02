package seacsp.data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.io.File;
import java.sql.SQLException;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.calculations.Timehistory;
import seacsp.db.DataCollectionDao;
import seacsp.db.InitializeDatabase;
import seacsp.db.TimehistoryDao;
import seacsp.logic.LogList;
import seacsp.file.ReadFile;

public class DataCollectionsTest {
    private DataCollections dataCollections;
    private DataCollection dataCollection0;
    private DataCollection dataCollection1;
    private LogList logList;
    
    @Before
    public void setUp() {
        this.logList = new LogList();
        ReadFile readFile = new ReadFile();
        this.dataCollections = new DataCollections(this.logList, readFile);
        File file0 = new File(System.getProperty("user.dir") + "/" + "TestFile0.txt");
        File file1 = new File(System.getProperty("user.dir") + "/" + "TestFile1.txt");
        try {
            this.dataCollection1 = this.dataCollections.addFile(file1); 
            this.dataCollection0 = this.dataCollections.addFile(file0);
           
        } catch (Exception e) {
            System.out.println("Something is wrong.");
        }
        CheckBoxTreeItem objTrue = new CheckBoxTreeItem();
        objTrue.setSelected(true);
        CheckBoxTreeItem objFalse = new CheckBoxTreeItem();
        objFalse.setSelected(false);
        
        this.dataCollection1.setReferenceToTreeItem(0, objTrue);
        this.dataCollection1.setReferenceToTreeItem(1, objFalse);
        
        this.dataCollection0.setReferenceToTreeItem(0, objFalse);
        this.dataCollection0.setReferenceToTreeItem(1, objTrue);
        this.dataCollection0.setReferenceToTreeItem(2, objFalse);
        this.dataCollection0.setReferenceToTreeItem(3, objTrue);
        this.dataCollection0.setReferenceToTreeItem(4, objTrue);        
    }
    
    private DataCollection initializeDataCollection1() {
        ArrayList<Double> list1 = new ArrayList<>();   
        list1.add(1.01);
        list1.add(1.02);
        list1.add(1.03);
        list1.add(1.04);
        ArrayList<Double> list2 = new ArrayList<>();   
        list2.add(2.01);
        list2.add(2.03);
        list2.add(2.05);
        list2.add(2.07);
        list2.add(2.09);
        list2.add(2.11);
        ArrayList<Double> list3 = new ArrayList<>();   
        list3.add(3.16);
        Timehistory timehistory1 = new Timehistory(list1, 0.01, "First th 1");
        Timehistory timehistory2 = new Timehistory(list2, 0.02, "Second th 1");
        Timehistory timehistory3 = new Timehistory(list3, 0.07, "Third th 1");
        ArrayList<Timehistory> list = new ArrayList<>();
        list.add(timehistory1);
        list.add(timehistory2);
        list.add(timehistory3);
        return new DataCollection("Collection 1", list);
    }
    
    private DataCollection initializeDataCollection2() {
        ArrayList<Double> list1 = new ArrayList<>();   
        list1.add(21.01);
        list1.add(21.02);
        list1.add(21.03);
        list1.add(21.04);
        ArrayList<Double> list2 = new ArrayList<>();   
        list2.add(22.01);
        list2.add(22.03);
        list2.add(22.05);
        list2.add(22.07);
        list2.add(22.09);
        list2.add(22.11);
        ArrayList<Double> list3 = new ArrayList<>();   
        list3.add(23.16);
        Timehistory timehistory1 = new Timehistory(list1, 2.01, "First th 2");
        Timehistory timehistory2 = new Timehistory(list2, 2.02, "Second th 2");
        Timehistory timehistory3 = new Timehistory(list3, 2.07, "Third th 2");
        ArrayList<Timehistory> list = new ArrayList<>();
        list.add(timehistory1);
        list.add(timehistory2);
        list.add(timehistory3);
        return new DataCollection("Collection 2", list);
    }
    
    @Test
    public void dataFilesExist() {        
        assertTrue(this.dataCollections != null);      
    }
    
    @Test
    public void fileAlreadyAdded() {
        File file1 = new File(System.getProperty("user.dir") + "/" + "TestFile1.txt");
        assertTrue(this.dataCollections.fileAlreadyAdded(file1));      
    }
    
    @Test
    public void fileAlreadyAddedToFiles() {
        File file1 = new File(System.getProperty("user.dir") + "/" + "TestFile1.txt");
        String message = "";
        try {
            this.dataCollections.addFile(file1);
        } catch (Exception e) {
            message = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: File TestFile1.txt already added.", message);      
    }
    
    @Test
    public void fileNotYetAdded() {
        File filex = new File(System.getProperty("user.dir") + "/" + "TestFilex.txt");
        assertFalse(this.dataCollections.fileAlreadyAdded(filex));      
    }
        
    @Test
    public void recalculationNeeded01() {
        Phii phii = new Phii(-0.1);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        assertTrue(this.dataCollections.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded02() {
        Phii phii = new Phii(0.01);
        Frequencies frequencies = new Frequencies();
        assertTrue(this.dataCollections.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded00() {
        Phii phii = new Phii(-0.1);
        Frequencies frequencies = new Frequencies();
        assertFalse(this.dataCollections.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded1() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataCollections.neededToRecalculate(frequencies, phii);
        assertFalse(this.dataCollections.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded2() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataCollections.neededToRecalculate(frequencies, phii);
        phii = new Phii(0.050001);
        assertTrue(this.dataCollections.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded3() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataCollections.neededToRecalculate(frequencies, phii);
        frequencies.equalDivision(3.0001, 5.5, 1);
        assertTrue(this.dataCollections.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded4() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataCollections.neededToRecalculate(frequencies, phii);
        frequencies.equalDivision(3.1, 5.5, 1);        
        assertFalse(this.dataCollections.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded5() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        assertTrue(this.dataCollections.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void calculationDone() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(2.1, 6.5, 1);
        this.dataCollections.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList = new ArrayList<>();
        this.dataCollections.getSpectrumList(spectrumList);
        assertEquals(4.29171705, spectrumList.get(0).getAccWithFrequency(0), 0.000001);
    }
    
    @Test
    public void noNeedForRecalculation() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies1 = new Frequencies();
        frequencies1.equalDivision(2.1, 6.5, 1);
        this.dataCollections.calculate(frequencies1, phii);
        Frequencies frequencies2 = new Frequencies();
        frequencies2.equalDivision(3.1, 7.5, 1);
        this.dataCollections.copyFrequencies(frequencies2.getFrequenceList());
        this.dataCollections.calculate(frequencies2, phii);
        ArrayList<Spectrum> spectrumList = new ArrayList<>();
        this.dataCollections.getSpectrumList(spectrumList);        
        assertEquals(4.29171705, spectrumList.get(0).getAccWithFrequency(0), 0.000001);
    }
    
    @Test
    public void getTimehistoryListsGivesTimehistoriesKey() {
        ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists = new ArrayList<>();
        dataCollections.getTimehistoryLists(timehistoryLists);
        double value = timehistoryLists.get(0).getKey().get(4);
        assertEquals(0.008, value, 0.000001);
    }
    
    @Test
    public void getTimehistoryListsGivesTimehistoriesValue() {
        ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists = new ArrayList<>();
        dataCollections.getTimehistoryLists(timehistoryLists);
        double value = timehistoryLists.get(0).getValue().get(4);
        assertEquals(0.00878124, value, 0.000001);
    }
    
    @Test
    public void saveToDataBaseWorks() {
        String testFile = "testFileForSeismicAccelerationSpectrumTest.db";
        File file1 = new File(testFile);
        DataCollectionDao dataCollectionDao = new DataCollectionDao();
        dataCollectionDao.setTimehistoryDao(new TimehistoryDao());
        dataCollectionDao.setDbFile(file1);
        InitializeDatabase initializeDatabase = new InitializeDatabase();
        try {
            initializeDatabase.initializeDatabase(testFile);            
        } catch (SQLException e) {
            System.out.println(e);
        }        
        this.dataCollections.saveTimehistoriesToDB(file1);
        DataCollections newDataCollections = new DataCollections(new LogList(), new ReadFile());
        ArrayList<DataCollection> dataCollectionsList = newDataCollections.readDataBase(file1);
        deleteDBFile(file1);
        assertEquals(0.00019844, dataCollectionsList.get(1).getTimehistories().get(2).getAcc(3), 0.000001);
    }
    
    public void deleteDBFile(File file) {
        if(file.delete()){
            
        } else {
            System.out.println("File doesn't exist in the project root directory");
        }
    }
    
    @Test
    public void readingDataFromDataBaseNotWork() {
        String testFile = "testFileForSeismicAccelerationSpectrumTestNotWork.db";
        File file1 = new File(testFile);
        LogList logList = new LogList();
        DataCollections newDataCollections = new DataCollections(logList, new ReadFile());
        ArrayList<DataCollection> dataCollectionsList = newDataCollections.readDataBase(file1);
        deleteDBFile(file1);
        assertEquals("Reading from database caused error.", logList.getLog().get(0));
    }
    
    @Test
    public void notCreateToDataBaseDoubleObject() {
        String testFile = "testFileForSeismicAccelerationSpectrumTest.db";
        File file1 = new File(testFile);
        DataCollectionDao dataCollectionDao = new DataCollectionDao();
        dataCollectionDao.setTimehistoryDao(new TimehistoryDao());
        dataCollectionDao.setDbFile(file1);
        InitializeDatabase initializeDatabase = new InitializeDatabase();
        try {
            initializeDatabase.initializeDatabase(testFile);            
        } catch (SQLException e) {
            System.out.println(e);
        }        
        this.dataCollections.saveTimehistoriesToDB(file1);
        this.dataCollections.saveTimehistoriesToDB(file1);
        deleteDBFile(file1);
        assertEquals("File TestFile0.txt already exist in the DB.", this.logList.getLog().get(this.logList.getLog().size()-1));
    }
}
