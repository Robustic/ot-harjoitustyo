package seacsp.calculations;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
        
public class PhiiTest {
    
    Phii phii;
    
    @Before
    public void setUp() {
        this.phii = new Phii(0.05);
    }

    @Test
    public void phiiExists() {        
        assertTrue(this.phii!=null);      
    }
    
    @Test
    public void correctInitializedValueOfPhii() {
        assertTrue(0.0499999 < this.phii.getPhii() && this.phii.getPhii() < 0.0500001);      
    }
    
    @Test
    public void correctValueOfPhii() {
        this.phii.setPhii(0.02);
        assertTrue(0.0199999 < this.phii.getPhii() && this.phii.getPhii() < 0.0200001);      
    }
}
