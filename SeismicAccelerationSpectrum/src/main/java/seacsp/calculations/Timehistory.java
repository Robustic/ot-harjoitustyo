package seacsp.calculations;

import java.util.*;

public class Timehistory {
    final private ArrayList<Double> timehistory;
    private double deltaT;
    final private String name;

    public Timehistory(ArrayList<Double> timehistory, double deltaT, String name) {
        this.timehistory = timehistory;
        this.deltaT = deltaT;
        this.name = name;
    }
    
    public Timehistory(String name) {
        this.timehistory = new ArrayList<>();
        this.name = name;
    }
    
    public Timehistory(double deltaT, String name) {
        this.timehistory = new ArrayList<>();
        this.deltaT = deltaT;
        this.name = name;
    }
    
    public void addNewAcc(double acc) {
        this.timehistory.add(acc);
    }
    
    public double getAcc(int i) {
        return this.timehistory.get(i);
    }

    public ArrayList<Double> getTimehistory() {
        return timehistory;
    }

    public void setDeltaT(double deltaT) {
        this.deltaT = deltaT;
    }

    public double getDeltaT() {
        return deltaT;
    }

    public String getName() {
        return name;
    }
    
    
}
