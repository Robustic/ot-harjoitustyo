package seacsp.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import seacsp.data.DataCollection;
import seacsp.calculations.Timehistory;

public class DataCollectionDaoTest {
    private TimehistoryDao timehistoryDao;
    private DataCollectionDao dataCollectionDao;
    private File file;
    
    @Before
    public void setUp() {
        String testFile = "testFileForSeismicAccelerationSpectrumTest";
        this.file = new File(testFile);
        this.timehistoryDao = new TimehistoryDao();
        this.dataCollectionDao = new DataCollectionDao();
        this.timehistoryDao.setDatabaseFile(new File(testFile));
        InitializeDatabase initializeDatabase = new InitializeDatabase();
        try {
            initializeDatabase.initializeDatabaseFile(testFile);            
        } catch (SQLException e) {
            System.out.println(e);
        }
        initializeDataCollection1();
        initializeDataCollection2();
    }
    
    private void initializeDataCollection1() {
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
        DataCollection dataCollection = new DataCollection("Collection 1", list);
        try {
            this.dataCollectionDao.setDatabaseFile(file);
            this.dataCollectionDao.setTimehistoryDao(timehistoryDao);
            this.dataCollectionDao.create(dataCollection);          
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void initializeDataCollection2() {
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
        DataCollection dataCollection = new DataCollection("Collection 2", list);
        try {
            this.dataCollectionDao.setDatabaseFile(file);
            this.dataCollectionDao.setTimehistoryDao(timehistoryDao);
            this.dataCollectionDao.create(dataCollection);          
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    @After 
    public void deleteDBFile() {
        if(this.file.delete()){
            
        } else {
            System.out.println("File doesn't exist in the project root directory");
        }
    }
    
    @Test
    public void dataCollectionDaoExist() {        
        assertTrue(this.dataCollectionDao != null);      
    }
    
    @Test
    public void getListWithNotNameWorks() {
        ArrayList<String> existingNames = new ArrayList<>();
        existingNames.add("Matti");
        existingNames.add("Pekka");
        existingNames.add("Collection 1");
        existingNames.add("Sauli");
        ArrayList<DataCollection> collectionList = null;
        try {
            collectionList = this.dataCollectionDao.listDataCollectionsWithNameNotExistInTheList(existingNames);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(22.11,  collectionList.get(0).getTimehistories().get(1).getAccelerationValueInTheIndex(5), 0.00001);      
    }
    
    @Test
    public void exceptionWhenNoGeneratedKeys() {
        String generatedException = "";
        try {
            String connectionPath = "jdbc:sqlite:" + this.file.toString();
            Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection");
            ResultSet rs = stmt.executeQuery();
            this.dataCollectionDao.generatedKey(stmt);
        } catch (Exception e) {
            generatedException = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: No generated sqldb-rows.", generatedException);
    }
    
    @Test
    public void dataCollectionWithGivenKeyExists() {
        boolean keyExist = false;
        try {
            String connectionPath = "jdbc:sqlite:" + this.file.toString();
            Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection");
            ResultSet rs = stmt.executeQuery();
            keyExist = this.dataCollectionDao.dataCollectionWithGivenNameExist("Collection 1");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        assertTrue(keyExist);
    }
    
    @Test
    public void dataCollectionWithGivenKeyNotExist() {
        boolean keyExist = true;
        try {
            String connectionPath = "jdbc:sqlite:" + this.file.toString();
            Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection");
            ResultSet rs = stmt.executeQuery();
            keyExist = this.dataCollectionDao.dataCollectionWithGivenNameExist("Collection 0");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        assertFalse(keyExist);
    }
    
    @Test
    public void dataCollectionWithGivenKeyIsOk() {
        DataCollection dataCollection = null;
        try {
            String connectionPath = "jdbc:sqlite:" + this.file.toString();
            Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection");
            ResultSet rs = stmt.executeQuery();
            dataCollection = this.dataCollectionDao.read(2);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(22.11, dataCollection.getTimehistories().get(1).getAccelerationValueInTheIndex(5), 0.0000001);
    }
    
    @Test
    public void dataCollectionWithGivenKeyNotExists() {
        DataCollection dataCollection = null;
        try {
            String connectionPath = "jdbc:sqlite:" + this.file.toString();
            Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection");
            ResultSet rs = stmt.executeQuery();
            dataCollection = this.dataCollectionDao.read(20);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        assertTrue(dataCollection == null);
    }
    
    @Test
    public void tablesExistInTheDatabase() {
        boolean result = false;
        result = this.dataCollectionDao.tablesDatacollectionAndTimehistoryExist();
        assertTrue(result);
    }
}
