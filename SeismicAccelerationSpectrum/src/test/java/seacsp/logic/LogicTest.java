package seacsp.logic;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.util.Pair;
import seacsp.data.DataCollection;

public class LogicTest {
    private Logic logic;
    
    @Before
    public void setUp() {
        this.logic = new Logic();        
    }
    
    @Test
    public void logicExists() {        
        assertTrue(this.logic != null);      
    }
    
    @Test
    public void txtFileReadingIsWorking1() {
        File file = new File("TestFile1.txt");
        DataCollection dataCollection = this.logic.addNewDataCollection(file);
        CheckBoxTreeItem objFalse = new CheckBoxTreeItem();
        objFalse.setSelected(false);
        CheckBoxTreeItem objTrue = new CheckBoxTreeItem();
        objTrue.setSelected(true); 
        dataCollection.setReferenceToTreeItemObject(0, objFalse);
        dataCollection.setReferenceToTreeItemObject(1, objTrue);
        ArrayList<ArrayList<Pair<Double, Double>>> timehistoryLists = this.logic.getTimehistoryXYValueLists();;
        assertEquals(0.006, timehistoryLists.get(0).get(3).getKey(), 0.0000001);
    }
    
    @Test
    public void txtFileReadingIsWorking2() {
        File file = new File("TestFile1.txt");
        DataCollection dataCollection = this.logic.addNewDataCollection(file);
        CheckBoxTreeItem objFalse = new CheckBoxTreeItem();
        objFalse.setSelected(false);
        CheckBoxTreeItem objTrue = new CheckBoxTreeItem();
        objTrue.setSelected(true); 
        dataCollection.setReferenceToTreeItemObject(0, objFalse);
        dataCollection.setReferenceToTreeItemObject(1, objTrue);
        ArrayList<ArrayList<Pair<Double, Double>>> timehistoryLists = this.logic.getTimehistoryXYValueLists();
        assertEquals(0.00252532, timehistoryLists.get(0).get(3).getValue(), 0.00000001);
    }
    
    @Test
    public void txtFileNotFound() {
        File file = new File("TestFileWhichNotFound.txt");
        DataCollection dataCollection = this.logic.addNewDataCollection(file);
        assertTrue(dataCollection == null);
    }
    
    @Test
    public void calculatingIsWorking1() {
        File file = new File("TestFile1.txt");
        DataCollection dataCollection = this.logic.addNewDataCollection(file);
        CheckBoxTreeItem objFalse = new CheckBoxTreeItem();
        objFalse.setSelected(false);
        CheckBoxTreeItem objTrue = new CheckBoxTreeItem();
        objTrue.setSelected(true); 
        dataCollection.setReferenceToTreeItemObject(0, objFalse);
        dataCollection.setReferenceToTreeItemObject(1, objTrue);
        this.logic.setEqualDivisionFrequence(2, 3.1, 1);
        this.logic.setPhiiValue(0.1);
        ArrayList<Pair<Double, Double>> listOfCalculatedValues = this.logic.getSpectrumXYValues();
        assertEquals(3.0, listOfCalculatedValues.get(1).getKey(), 0.0000001);
    }
    
    @Test
    public void calculatingIsWorking2() {
        File file = new File("TestFile1.txt");
        DataCollection dataCollection = this.logic.addNewDataCollection(file);
        CheckBoxTreeItem objFalse = new CheckBoxTreeItem();
        objFalse.setSelected(false);
        CheckBoxTreeItem objTrue = new CheckBoxTreeItem();
        objTrue.setSelected(true); 
        dataCollection.setReferenceToTreeItemObject(0, objTrue);
        dataCollection.setReferenceToTreeItemObject(1, objTrue);
        this.logic.setEqualDivisionFrequence(2, 3.1, 1);
        this.logic.setPhiiValue(0.1);
        ArrayList<Pair<Double, Double>> listOfCalculatedValues = this.logic.getSpectrumXYValues();
        assertEquals(9.143752, listOfCalculatedValues.get(1).getValue(), 0.000001);
    }
    
    @Test
    public void fileNameIsEmpty() {
        String initializingResult = this.logic.newDatabaseFile("-1");
        assertEquals("", initializingResult);
    }
    
    @Test
    public void initailizingWorks() {
        String initializingResult = this.logic.newDatabaseFile("NewTemporaryTestFile");
        deleteDBFile(new File("NewTemporaryTestFile.db"));
        assertEquals("NewTemporaryTestFile.db", initializingResult);        
    }
    
    public void deleteDBFile(File file) {
        if(!file.delete()) {
            System.out.println("File doesn't exist in the project root directory");
        }
    }
    
    @Test
    public void initailizingNotDoneFileAlreadyExists() {
        String initializingResult = this.logic.newDatabaseFile("NewTemporaryTestFile");        
        initializingResult = this.logic.newDatabaseFile("NewTemporaryTestFile");
        deleteDBFile(new File("NewTemporaryTestFile.db"));
        assertEquals("", initializingResult);
        LogList logList = this.logic.getLogList();
        assertEquals("New database not initialized. Filename already exists.", logList.getLogMessagesAsStringList().get(logList.getLogMessagesAsStringList().size() - 1));
    }
    
    @Test
    public void dataIsWrittenAndReadedCorrectlyFromDataBase() {
        File file = new File("TestFile1.txt");
        DataCollection dataCollection = this.logic.addNewDataCollection(file);
        CheckBoxTreeItem objFalse = new CheckBoxTreeItem();
        objFalse.setSelected(false);
        CheckBoxTreeItem objTrue = new CheckBoxTreeItem();
        objTrue.setSelected(true); 
        dataCollection.setReferenceToTreeItemObject(0, objTrue);
        dataCollection.setReferenceToTreeItemObject(1, objTrue);
        this.logic.newDatabaseFile("TemporaryFileNameToTestDataBaseReadingAndWriting");
        this.logic.saveDataCollectionsToDatabase();
        Logic newLogic = new Logic();        
        newLogic.setDatabaseFile(new File("TemporaryFileNameToTestDataBaseReadingAndWriting.db"));
        ArrayList<DataCollection> newDataCollections = newLogic.readDataCollectionsFromDatabase();
        deleteDBFile(new File("TemporaryFileNameToTestDataBaseReadingAndWriting.db"));
        assertEquals(0.00393687, newDataCollections.get(0).getTimehistories().get(1).getAccelerationValueInTheIndex(5), 0.0000001);
    }
}
