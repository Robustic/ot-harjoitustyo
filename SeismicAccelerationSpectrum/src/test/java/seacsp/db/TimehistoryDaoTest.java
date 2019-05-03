package seacsp.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import java.util.*;
import java.io.File;
import java.sql.SQLException;
import seacsp.calculations.Timehistory;

public class TimehistoryDaoTest {
    private TimehistoryDao timehistoryDao;
    private File file;
    
    @Before
    public void setUp() {
        String testFile = "testFileForSeismicAccelerationSpectrumTest";
        this.file = new File(testFile);
        this.timehistoryDao = new TimehistoryDao();
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
        Timehistory timehistory1 = new Timehistory(list1, 0.01, "First th");
        Timehistory timehistory2 = new Timehistory(list2, 0.02, "Second th");
        Timehistory timehistory3 = new Timehistory(list3, 0.07, "Third th");
        this.timehistoryDao.setDbFile(this.file);
        InitializeDatabase initializeDatabase = new InitializeDatabase();
        try {
            initializeDatabase.initializeDatabase(testFile);
            this.timehistoryDao.setDataCollectionId(313);
            this.timehistoryDao.create(timehistory1);
            this.timehistoryDao.setDataCollectionId(311);
            this.timehistoryDao.create(timehistory2);
            this.timehistoryDao.setDataCollectionId(313);
            this.timehistoryDao.create(timehistory3);            
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
    public void timehistoryDaoExist() {        
        assertTrue(this.timehistoryDao != null);      
    }
    
    @Test
    public void timehistory1TableExist() {
        double firstInTheList = 0;
        try {
            firstInTheList = this.timehistoryDao.getTableAsList(1).get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(1.01, firstInTheList, 0.000001);      
    }
    
    @Test
    public void dataIsRightInDatabase30() {
        Timehistory timehistoryTest = null;
        try {
            timehistoryTest = this.timehistoryDao.read(3);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(3.16, timehistoryTest.getAcc(0) , 0.0001);      
    }
    
    @Test
    public void dataIsRightInDatabase10() {
        Timehistory timehistoryTest = null;
        try {
            timehistoryTest = this.timehistoryDao.read(1);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(1.01, timehistoryTest.getAcc(0) , 0.0001);      
    }
    
    @Test
    public void dataIsRightInDatabase11() {
        Timehistory timehistoryTest = null;
        try {
            timehistoryTest = this.timehistoryDao.read(1);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(1.02, timehistoryTest.getAcc(1) , 0.0001);      
    }    
    @Test
    public void dataIsRightInDatabase12() {
        Timehistory timehistoryTest = null;
        try {
            timehistoryTest = this.timehistoryDao.read(1);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(1.03, timehistoryTest.getAcc(2) , 0.0001);      
    }
    
    @Test
    public void dataIsRightInDatabase13() {
        Timehistory timehistoryTest = null;
        try {
            timehistoryTest = this.timehistoryDao.read(1);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(1.04, timehistoryTest.getAcc(3) , 0.0001);      
    }
    
    @Test
    public void givenTimehistoryListWithIdIsOk() {
        ArrayList<Timehistory> timehistoryListTest = null;
        try {
            timehistoryListTest = this.timehistoryDao.listWithCollectionId(313);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(3.16, timehistoryListTest.get(1).getAcc(0), 0.0001);      
    }
    
    @Test
    public void givenTimehistoryListWithIdIsOkDeltaTIsRight() {
        ArrayList<Timehistory> timehistoryListTest = null;
        try {
            timehistoryListTest = this.timehistoryDao.listWithCollectionId(313);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(0.07, timehistoryListTest.get(1).getDeltaT(), 0.0001);      
    }
    
    @Test
    public void givenTimehistoryListWithIdIsOkNameIsRight() {
        ArrayList<Timehistory> timehistoryListTest = null;
        try {
            timehistoryListTest = this.timehistoryDao.listWithCollectionId(313);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals("Third th", timehistoryListTest.get(1).getName());      
    }
    
    @Test
    public void exceptionWhenNoGeneratedKeys() {
        String generatedException = "";
        try {
            String connectionPath = "jdbc:sqlite:" + this.file.toString();
            Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timehistory");
            ResultSet rs = stmt.executeQuery();
            this.timehistoryDao.generatedKey(stmt);
        } catch (Exception e) {
            generatedException = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: No generated sqldb-rows.", generatedException);
    }
    
    @Test
    public void exceptionWhenTimehistoryWithKey() {
        Timehistory getTimehistory = null;
        try {
            getTimehistory = this.timehistoryDao.read(133);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertTrue(getTimehistory == null);
    }
}
