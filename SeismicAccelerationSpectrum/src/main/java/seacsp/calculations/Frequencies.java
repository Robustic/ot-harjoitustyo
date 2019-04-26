package seacsp.calculations;

import java.util.*;

/**
 * Class capsulate list of the frequencies.
 */
public class Frequencies {
    private ArrayList<Double> frequenceList;

    /**
     * Constructor.
     */
    public Frequencies() {
        this.frequenceList = new ArrayList<>();
    }    
    
    /**
     * Defines division of the frequencies according to the ASCE 4-10.
     */
    public void asceDivision() {
        equalDivision(0.1, 5.005, 0.01);
    }
    
    /**
     * Method defines division of the frequencies according to the input.
     *
     * @param   start   start point [Hz] of the division
     *
     * @param   end   end point [Hz] of the division
     * 
     * @param   divisionHz   division as [Hz]
     */
    public void equalDivision(double start, double end, double divisionHz) {
        this.frequenceList.clear();
        double z = (end - start) / divisionHz;
        int k = (int) z;
        for (int i = 0; i <= k; i++) {
            frequenceList.add(start + i * divisionHz);
        }
    }

    /**
     * Get method for frequency list.
     * 
     * @return frequency list
     */
    public ArrayList<Double> getFrequenceList() {
        return frequenceList;
    }
    
    /**
     * Get method for frequency value in the index i.
     * 
     * @param   i   index
     * 
     * @return frequency value in the index i
     */
    public double getFrequency(int i) {
        return frequenceList.get(i);
    }
    
    /**
     * Get method for number of the frequency values.
     * 
     * @return size of the frequencies in the list
     */
    public int numberOfFrequencies() {
        return frequenceList.size();
    }
}
