package seacsp.calculations;

import java.util.*;

/**
 * Class capsulate time history lists.
 */
public class Timehistory {
    final private ArrayList<Double> timehistory;
    private double deltaT;
    final private String name;
    
    /**
     * Constructor.
     * 
     * @param   timehistory   timehistory for Timehistory
     * 
     * @param   deltaT   deltaT for Timehistory
     * 
     * @param   name   name for Timehistory
     */
    public Timehistory(ArrayList<Double> timehistory, double deltaT, String name) {
        this.timehistory = timehistory;
        this.deltaT = deltaT;
        this.name = name;
    }
    
    /**
     * Constructor with input name.
     * 
     * @param   name   name for Timehistory
     */
    public Timehistory(String name) {
        this.timehistory = new ArrayList<>();
        this.name = name;
    }
    
    /**
     * Constructor with input deltaT and name.
     * 
     * @param   deltaT   deltaT for Timehistory
     * 
     * @param   name   name for Timehistory
     */
    public Timehistory(double deltaT, String name) {
        this.timehistory = new ArrayList<>();
        this.deltaT = deltaT;
        this.name = name;
    }
    
    /**
     * Adds new acceleration value to Timehistory -list.
     * 
     * @param   acc   new acceleration value
     */
    public void addNewAcc(double acc) {
        this.timehistory.add(acc);
    }
    
    /**
     * Returns acceleration value in the index i.
     * 
     * @param   i   index
     * 
     * @return acceleration value in the index i
     */
    public double getAcc(int i) {
        return this.timehistory.get(i);
    }
    
    /**
     * Returns time history as a list.
     * 
     * @return time history as a list
     */
    public ArrayList<Double> getTimehistory() {
        return timehistory;
    }

    /**
     * Set method for deltaT-value.
     *
     * @param   deltaT   deltaT
     */
    public void setDeltaT(double deltaT) {
        this.deltaT = deltaT;
    }

    /**
     * Get method for deltaT.
     * 
     * @return adeltaT
     */
    public double getDeltaT() {
        return deltaT;
    }

    /**
     * Get method for name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
    
    
}
