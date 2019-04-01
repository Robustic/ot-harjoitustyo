package seacsp.calculations;

import java.util.*;

public class Timehistory {
    private ArrayList<Double> timehistory;
    private double deltaT;
    private String fileName;
    private String name;

    public Timehistory(ArrayList<Double> timehistory, double deltaT, String fileName, String name) {
        this.timehistory = timehistory;
        this.deltaT = deltaT;
        this.fileName = fileName;
        this.name = name;
    }

    public ArrayList<Double> getTimehistory() {
        return timehistory;
    }

    public double getDeltaT() {
        return deltaT;
    }

    public String getFileName() {
        return fileName;
    }

    public String getName() {
        return name;
    }
    
    
}
