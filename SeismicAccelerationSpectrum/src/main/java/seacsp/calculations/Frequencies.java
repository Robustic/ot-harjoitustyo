package seacsp.calculations;

import java.util.*;

public class Frequencies {
    private ArrayList<Double> frequenceList;

    public Frequencies() {
        this.frequenceList = new ArrayList<>();
    }    
    
    public void asceDivision() {
        equalDivision(0.1, 5.005, 0.01);
    }
    
    public void equalDivision(double start, double end, double divisionHz) {
        this.frequenceList.clear();
        double z = (end - start) / divisionHz;
        int k = (int) z;
        for (int i = 0; i <= k; i++) {
            frequenceList.add(start + i * divisionHz);
        }
    }

    public ArrayList<Double> getFrequenceList() {
        return frequenceList;
    }
    
    public double getFrequency(int i) {
        return frequenceList.get(i);
    }
    
    public int numberOfFrequencies() {
        return frequenceList.size();
    }
}
