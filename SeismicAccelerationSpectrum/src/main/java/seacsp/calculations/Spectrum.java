package seacsp.calculations;

import java.util.*;
import java.lang.*;

public class Spectrum {
    private Frequencies frequencies;
    private Timehistory timehistory;
    private Phii phii;
    private ArrayList<Double> accelerationList;
    private double omegaD;
    private double omegaN;
    private double phi;
    private double deltaT;
    private ArrayList<Double> timeHistoryList;

    public Spectrum(Frequencies frequencies, Timehistory timehistory, Phii phii) {
        this.frequencies = frequencies;
        this.timehistory = timehistory;
        this.phii = phii;
        this.accelerationList = new ArrayList<>();
    }
    
    public void calculateSpectrum() {
        this.phi = this.phii.getPhii();
        this.deltaT = this.timehistory.getDeltaT();
        this.timeHistoryList = this.timehistory.getTimehistory();
        for (int i = 0; i < this.frequencies.numberOfFrequencies(); i++) {
            this.omegaN = 2 * Math.PI * this.frequencies.getFrequences().get(i);
            this.omegaD = this.omegaN * Math.sqrt(1 - this.phi * this.phi);            
            accelerationList.add(calculateForOneFrequency());
        }
    }
    
    public double calculateForOneFrequency() {
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

    public ArrayList<Double> getAccelerationList() {
        return accelerationList;
    }
    
}
