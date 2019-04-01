package seacsp.calculations;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class SpectrumTest {
    
    Spectrum spectrum;
    
    @Before
    public void setUp() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(1, 50.5, 1);
        ArrayList<Double> timehistoryList = new ArrayList<>();
        for(int i = 0; i <= 1100; i++) {
            timehistoryList.add(Math.sin(9.425 * i * 0.01));
        }
        Timehistory timehistory = new Timehistory(timehistoryList, 0.01, "", "");
        this.spectrum = new Spectrum(frequencies, timehistory, phii);
        this.spectrum.calculateSpectrum();
    }

    @Test
    public void spectrumExists() {        
        assertTrue(this.spectrum != null);      
    }
    
    @Test
    public void correctValueOfMaximumAcceleration1() {
        double acceleration = this.spectrum.getAccelerationList().get(1-1);
        assertTrue(1.65245375 < acceleration && acceleration < 1.65245376);      
    }
    
    @Test
    public void correctValueOfMaximumAcceleration50() {
        double acceleration = this.spectrum.getAccelerationList().get(50-1);
        assertTrue(0.172626019 < acceleration && acceleration < 0.172626021);      
    }
}
