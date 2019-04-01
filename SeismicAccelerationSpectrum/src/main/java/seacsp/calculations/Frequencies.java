package seacsp.calculations;

import java.util.*;

public class Frequencies {
    private ArrayList<Double> frequencelist;

    public Frequencies() {
        this.frequencelist = new ArrayList<>();
    }
    
    public void equalDivision(double start, double end, double divisionHz) {
        double z = (end - start) / divisionHz;
        int k = (int) z;
        for (int i = 0; i <= k; i++) {
            frequencelist.add(start + i * divisionHz);
        }
    }

    public ArrayList<Double> getFrequences() {
        return frequencelist;
    }
    
    public int numberOfFrequencies() {
        return frequencelist.size();
    }
}
