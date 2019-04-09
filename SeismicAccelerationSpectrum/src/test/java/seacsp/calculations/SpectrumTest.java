package seacsp.calculations;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class SpectrumTest {    
    private Spectrum spectrum;
    
    @Before
    public void setUp() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(1, 50.5, 1);
        ArrayList<Double> timehistoryList = new ArrayList<>();
        for(int i = 0; i <= 1100; i++) {
            timehistoryList.add(Math.sin(9.425 * i * 0.01));
        }
        Timehistory timehistory = new Timehistory(timehistoryList, 0.01, "");
        this.spectrum = new Spectrum();
        this.spectrum.calculateSpectrum(frequencies, timehistory, phii);
    }

    @Test
    public void spectrumExists() {        
        assertTrue(this.spectrum != null);      
    }
    
    @Test
    public void correctValueOfMaximumAcceleration1() {
        double acceleration = this.spectrum.getAccelerationList().get(1-1);
        assertEquals(1.65245375, acceleration, 0.00000001);
    }
    
    @Test
    public void correctValueOfMaximumAcceleration50() {
        double acceleration = this.spectrum.getAccelerationList().get(50-1);
        assertEquals(0.172626020, acceleration, 0.000000001);
    }
    
    @Test
    public void correctValueWhenAskedFrequency() {
        double acceleration = this.spectrum.getAccelerationList().get(50-1);
        assertEquals(1.504347408, this.spectrum.getAccWithFrequency(3), 0.000000001);
    }
}
