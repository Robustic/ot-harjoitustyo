package seacsp.logic;

import java.util.ArrayList;
import javafx.scene.control.TextArea;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogListTest {    
    private LogList logList;
    private TextArea logTextArea;
    
    @Before
    public void setUp() {
        this.logList = new LogList();
        this.logTextArea = null;
        this.logList.setLogTextArea(this.logTextArea);
    }

    @Test
    public void logListExists() {        
        assertTrue(this.logList!=null);      
    }
    
    @Test
    public void logListIsEmptyWhenInitialized() {
        assertEquals(0, this.logList.getLog().size());      
    }
    
    @Test
    public void logListSavesRightValue() {
        this.logList.addLog("This is logtext.");
        assertEquals("This is logtext.", this.logList.getLog().get(this.logList.getLog().size() - 1));      
    }
    
    
}
