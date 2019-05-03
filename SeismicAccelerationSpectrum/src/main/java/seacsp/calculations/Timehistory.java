package seacsp.calculations;

import java.util.ArrayList;

/**
 * Class to capsulate time history as a list.
 */
public class Timehistory {
    final private ArrayList<Double> timehistory;
    private double deltaT;
    final private String name;
    
    /**
     * Constructor.
     * 
     * @param   name   name for the time history
     */
    public Timehistory(String name) {
        this.timehistory = new ArrayList<>();
        this.name = name;
    }
    
    /**
     * Constructor.
     * 
     * @param   timehistory   time history as a list
     * 
     * @param   deltaT   time step between time history values
     * 
     * @param   name   name for the time history
     */
    public Timehistory(ArrayList<Double> timehistory, double deltaT, String name) {
        this.timehistory = timehistory;
        this.deltaT = deltaT;
        this.name = name;
    }
    
    /**
     * Method to add new acceleration value to the end of the time history list.
     * 
     * @param   acc   new acceleration value
     */
    public void addNewAccelerationValue(double acc) {
        this.timehistory.add(acc);
    }
    
    /**
     * Returns acceleration value in the index i.
     * 
     * @param   i   index
     * 
     * @return acceleration value in the index i
     */
    public double getAccelerationValueInTheIndex(int i) {
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
     * Set method for the time step value.
     *
     * @param   deltaT   time step value
     */
    public void setDeltaT(double deltaT) {
        this.deltaT = deltaT;
    }

    /**
     * Get method for the time step value.
     * 
     * @return time step value
     */
    public double getDeltaT() {
        return deltaT;
    }

    /**
     * Get method for the time history name.
     * 
     * @return time history name
     */
    public String getName() {
        return name;
    }
    
    
}
