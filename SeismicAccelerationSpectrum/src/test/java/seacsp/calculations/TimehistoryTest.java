package seacsp.calculations;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class TimehistoryTest {
    private Timehistory timehistory;
    
    @Before
    public void setUp() {        
        this.timehistory = new Timehistory("history");
    }
    
    @Test
    public void timehistoryExists() {        
        assertTrue(this.timehistory != null);      
    }
    
    @Test
    public void correctInitialName() {
        assertEquals("history", this.timehistory.getName());
    }
    
    @Test
    public void correctAddedAcc() {
        this.timehistory.addNewAcc(1.23456);
        assertEquals(1.23456, this.timehistory.getTimehistory().get(0), 0.00001);
    }
    
    @Test
    public void correctAddedDeltaT() {
        this.timehistory.setDeltaT(6.23456);
        assertEquals(6.23456, this.timehistory.getDeltaT(), 0.00001);
    }
}
