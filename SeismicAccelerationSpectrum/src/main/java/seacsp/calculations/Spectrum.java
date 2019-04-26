package seacsp.calculations;

import java.util.*;
import java.lang.*;

/**
 * Class calculates and capsulate acceleration spectrums with the given frequence values.
 */
public class Spectrum {    
    private ArrayList<Double> accelerationList;
    private double omegaD;
    private double omegaN;
    private double phi;
    private double deltaT;
    private ArrayList<Double> timeHistoryList;

    /**
     * Constructor.
     */
    public Spectrum() {
        this.accelerationList = new ArrayList<>();
    }
    
    /**
     * Get method for the acceleration value in the index i.
     * 
     * @param   i   index
     * 
     * @return acceleration value in the index i
     */
    public double getAccWithFrequency(int i) {
        return this.accelerationList.get(i);
    }
    
    /**
     * Method calculates acceleration spectrum according to the given input.
     *
     * @param   frequencies   frequency list as class
     *
     * @param   timehistory   time history list as class
     * 
     * @param   phii   damping value as class
     */
    public void calculateSpectrum(Frequencies frequencies, Timehistory timehistory, Phii phii) {
        this.phi = phii.getPhii();
        this.deltaT = timehistory.getDeltaT();
        this.timeHistoryList = timehistory.getTimehistory();
        ArrayList<Double> frequenciesList = frequencies.getFrequenceList();
        for (int i = 0; i < frequenciesList.size(); i++) {
            this.omegaN = 2 * Math.PI * frequenciesList.get(i);
            this.omegaD = this.omegaN * Math.sqrt(1 - this.phi * this.phi);            
            accelerationList.add(calculateForOneFrequency());
        }
    }
    
    private double calculateForOneFrequency() {
        double x, x1 = 0, x2 = 0, xmax = 0;
        double y, y1 = 0;
        for (int j = 0; j < this.timeHistoryList.size(); j++) {
            y = this.timeHistoryList.get(j);
            x = 2 * Math.exp(-this.phi * this.omegaN * this.deltaT) 
                    * Math.cos(this.omegaD * this.deltaT) * x1;
            x -= Math.exp(-2 * this.phi * this.omegaN * this.deltaT) * x2;
            x += 2 * this.phi * this.omegaN * this.deltaT * y;
            x += this.omegaN * this.deltaT * Math.exp(-this.phi * this.omegaN * this.deltaT) 
                    * ((this.omegaN / this.omegaD * (1 - 2 * this.phi * this.phi)) 
                    * Math.sin(this.omegaD * this.deltaT) 
                    - 2 * this.phi * Math.cos(this.omegaD * this.deltaT)) * y1;
            x2 = x1;
            x1 = x;                
            y1 = y;
            xmax = Math.max(xmax, Math.abs(x));
        }
        return xmax;
    }

    /**
     * Get method for acceleration list.
     * 
     * @return acceleration list
     */
    public ArrayList<Double> getAccelerationList() {
        return accelerationList;
    }
    
}
