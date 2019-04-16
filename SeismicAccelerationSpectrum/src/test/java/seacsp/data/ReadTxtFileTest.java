package seacsp.data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.io.File;

public class ReadTxtFileTest {
    private ReadTxtFile readTxtFile;
    
    @Before
    public void setUp() {
        File file = new File(System.getProperty("user.dir") + "/" + "TestFile0.txt");
        this.readTxtFile = new ReadTxtFile(file);        
    }
    
    @Test
    public void readTxtFileExists() {        
        assertTrue(this.readTxtFile != null);      
    }
        
    @Test
    public void correctValueOfDeltaT() {
        DataFile dataFile = null;
        try { 
            dataFile = this.readTxtFile.readTxtFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals(0.002, dataFile.getTimehistories().get(0).getDeltaT(), 0.000001);
    }
    
    @Test
    public void correctNumberOfTimehistories() {
        DataFile dataFile = null;
        try { 
            dataFile = this.readTxtFile.readTxtFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals(5, dataFile.getTimehistories().size());
    }
    
    @Test
    public void correctHeaderOfTimehistory5() {
        DataFile dataFile = null;
        try { 
            dataFile = this.readTxtFile.readTxtFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals("No5", dataFile.getTimehistories().get(4).getName());
    }
    
    @Test
    public void correctNumberOfTimesteps() {
        DataFile dataFile = null;
        try { 
            dataFile = this.readTxtFile.readTxtFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals(10001, dataFile.getTimehistories().get(4).getTimehistory().size());
    }
    
    @Test
    public void correctLastValueOfTimehistory5() {
        DataFile dataFile = null;
        try { 
            dataFile = this.readTxtFile.readTxtFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals(0, dataFile.getTimehistories().get(4).getAcc(dataFile.getTimehistories().get(4).getTimehistory().size() - 1), 0.000000001);
    }
    
    @Test
    public void correctSecondLastValueOfTimehistory5() {
        DataFile dataFile = null;
        try { 
            dataFile = this.readTxtFile.readTxtFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals(-0.00038879, dataFile.getTimehistories().get(4).getAcc(dataFile.getTimehistories().get(4).getTimehistory().size() - 2), 0.000000001);
    }
    
    @Test
    public void numberIsNumeric() {        
        assertTrue(this.readTxtFile.isNumeric("3.044"));
    }
    
    @Test
    public void textIsNotNumeric() {        
        assertFalse(this.readTxtFile.isNumeric("3.0s44"));
    }
    
    @Test
    public void columnsAreCleanedProperly() {
        String[] columnsIn = {" pos ea", "", " ", "\t", "a \taae"};
        assertEquals("aaae", this.readTxtFile.cleanColumns(columnsIn).get(1));
    }
    
    @Test
    public void tooShortTimeLineNotAllowed() {
        ArrayList<Double> timeLine = new ArrayList<>();
        timeLine.add(0.0);
        String catchedString = "";
        try {
            this.readTxtFile.addDeltaTValues(timeLine);
        } catch (Exception e) {
            catchedString = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: File TestFile0.txt contains ineligible data. Less than 2 data rows.", catchedString);
    }
    
    @Test
    public void timeLineNotCrowingLinearilyPlus() {
        ArrayList<Double> timeLine = new ArrayList<>();
        timeLine.add(0.0);
        timeLine.add(1.0);
        timeLine.add(2.0);
        timeLine.add(3.0000011);
        timeLine.add(4.0);
        String catchedString = "";
        try {
            this.readTxtFile.addDeltaTValues(timeLine);
        } catch (Exception e) {
            catchedString = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: In file TestFile0.txt time is not increasing linerially.", catchedString);
    }
    
    @Test
    public void timeLineDecreasing() {
        ArrayList<Double> timeLine = new ArrayList<>();
        timeLine.add(0.0);
        timeLine.add(-1.0);
        timeLine.add(-2.0);
        timeLine.add(-3.0);
        timeLine.add(-4.0);
        String catchedString = "";
        try {
            this.readTxtFile.addDeltaTValues(timeLine);
        } catch (Exception e) {
            catchedString = e.toString();
            System.out.println(e);
        }
        assertEquals("java.lang.IllegalArgumentException: File TestFile0.txt time is decreasing.", catchedString);
    }
    
    @Test
    public void timeLineNotCrowingLinearilyMinus() {
        ArrayList<Double> timeLine = new ArrayList<>();
        timeLine.add(0.0);
        timeLine.add(1.0);
        timeLine.add(2.0);
        timeLine.add(2.9999989);
        timeLine.add(4.0);
        String catchedString = "";
        try {
            this.readTxtFile.addDeltaTValues(timeLine);
        } catch (Exception e) {
            catchedString = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: In file TestFile0.txt time is not increasing linerially.", catchedString);
    }
    
    @Test
    public void emptyString() {
        String emptyString = "";
        Scanner scanner = new Scanner(emptyString);
        String catchedString = "";
        try {
            this.readTxtFile.addHeaders(scanner);
        } catch (Exception e) {
            catchedString = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: File TestFile0.txt contains ineligible data. No headers.", catchedString);
    }
    
    @Test
    public void ghostFileNotFound() {
        File file = new File(System.getProperty("user.dir") + "/" + "FileNotFound.txt");
        this.readTxtFile = new ReadTxtFile(file);
        String catchedString = "";
        try {
            this.readTxtFile.readFileLineByLine();
        } catch (Exception e) {
            catchedString = "File not found.";
        }
        assertEquals("File not found.", catchedString);
    }
    
    @Test
    public void numberOfHeadersDifferentAsNumberOfColumns1() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.0");
        String catchedString = "";
        try { 
            this.readTxtFile.readTxtFile();
            this.readTxtFile.addColumnsContent(columns, new ArrayList<Double>());
        } catch (Exception e) {
            catchedString = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: File TestFile0.txt contains ineligible data. Number of columns do not match.", catchedString);
    }
    
    @Test
    public void numberOfHeadersDifferentAsNumberOfColumns2() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.0");
        String catchedString = "";
        try { 
            this.readTxtFile.readTxtFile();
            this.readTxtFile.addColumnsContent(columns, new ArrayList<Double>());
        } catch (Exception e) {
            catchedString = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: File TestFile0.txt contains ineligible data. Number of columns do not match.", catchedString);
    }
    
    @Test
    public void columnsContainOtherThanNumbers() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("0.0");
        columns.add("0.0");
        columns.add("0.000");
        columns.add("0.0O0");
        columns.add("0.0");
        columns.add("0.0");
        String catchedString = "";
        try { 
            this.readTxtFile.readTxtFile();
            this.readTxtFile.addColumnsContent(columns, new ArrayList<Double>());
        } catch (Exception e) {
            catchedString = e.toString();
        }
        assertEquals("java.lang.NumberFormatException: File TestFile0.txt contains ineligible data. Numbers contains bad data.", catchedString);
    }
}
