package seacsp.calculations;

import java.util.ArrayList;

/**
 * Class to capsulate list of the frequencies.
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
     * Method defines division of the frequencies according to the input.
     *
     * @param   start   start point [Hz] of the division
     *
     * @param   end   end point [Hz] of the division
     * 
     * @param   divisionHz   division as [Hz]
     */
    public void setEqualDivision(double start, double end, double divisionHz) {
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
    public double getFrequencyInTheIndex(int i) {
        return frequenceList.get(i);
    }
    
    /**
     * Get method for the number of the frequency values.
     * 
     * @return number of the frequencies in the list
     */
    public int numberOfFrequenciesInTheList() {
        return frequenceList.size();
    }
}
