package seacsp.file;

import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ReadFileTest {
    private File file;
    private ReadFile readFile;
    
    @Before
    public void setUp() {
        this.readFile = new ReadFile();
        this.file = new File(System.getProperty("user.dir") + "/" + "TestFile1.txt");
    }
    
    @Test
    public void readFileExists() {        
        assertTrue(this.readFile != null);      
    }
        
    @Test
    public void fileIsReadedProperly() {
        String fileAsString = "";
        try { 
            fileAsString = this.readFile.readFileLineByLine(this.file);
        } catch (Exception e) {
            System.out.println(e);
        }
        String[] lines = fileAsString.split(System.getProperty("line.separator"));
        assertEquals("   0.006    0.00647936    0.00252532", lines[5]);
    }
    
    @Test
    public void fileExist() {
        assertTrue(this.readFile.checkThatFileExists(this.file));
    }
    
    @Test
    public void fileNotExist() {
        String fileAsString = "";
        File file2 = new File(System.getProperty("user.dir") + "/" + "TestFileNNN.txt");
        assertFalse(this.readFile.checkThatFileExists(file2));
    }
}
