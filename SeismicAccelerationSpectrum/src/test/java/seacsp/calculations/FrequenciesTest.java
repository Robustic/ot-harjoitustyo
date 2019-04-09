package seacsp.calculations;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class FrequenciesTest {
    private Frequencies frequencies;
    
    @Before
    public void setUp() {        
        this.frequencies = new Frequencies();
        this.frequencies.equalDivision(1, 50.5, 1);        
    }
    
    @Test
    public void frequenciesExists() {        
        assertTrue(this.frequencies != null);      
    }
    
    @Test
    public void correctValueOfFrequency() {
        assertEquals(4.0, this.frequencies.getFrequency(3), 0.00000001);
    }
    
    @Test
    public void correctNumberOfFrequencies() {
        assertEquals(50, this.frequencies.numberOfFrequencies());
    }
}
