package seacsp.logic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

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
}
