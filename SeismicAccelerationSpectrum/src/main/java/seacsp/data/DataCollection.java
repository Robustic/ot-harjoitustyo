package seacsp.data;

import java.util.ArrayList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.calculations.Timehistory;

public class DataCollection {
    final private String name;
    final private ArrayList<Timehistory> timehistories;
    final private ArrayList<CheckBoxTreeItem> treeItems;
    private boolean calculated;    
    final private ArrayList<Spectrum> spectrums;

    public DataCollection(String name, ArrayList<Timehistory> timehistories) {
        this.name = name;
        this.timehistories = timehistories;
        this.treeItems = new ArrayList<>();
        for (Timehistory timehistory : this.timehistories) {
            this.treeItems.add(null);
        }
        this.calculated = false;
        this.spectrums = new ArrayList<>();
    }
    
    public void setCalculatedTrue() {
        this.calculated = true;
    }
    
    public void setCalculatedFalse() {
        this.calculated = false;
    }

    public boolean isCalculated() {
        return this.calculated;
    }   
    
    public void calculate(Frequencies frequencies, Phii phii) {
        if (isCalculated() == false) {
            this.spectrums.clear();
            for (int i = 0; i < this.timehistories.size(); i++) {
                this.spectrums.add(new Spectrum());
                this.spectrums.get(i).calculateSpectrum(frequencies, this.timehistories.get(i), phii);
            }
            setCalculatedTrue(); 
        }
    }
    
    public void addSpectrumsToList(ArrayList<Spectrum> spectrumList) {
        for (int i = 0; i < this.spectrums.size(); i++) {
            if (this.treeItems.get(i).isSelected()) {
                spectrumList.add(this.spectrums.get(i));
            }
        }
    }
    
    public void getTimehistoryLists(ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists) {        
        for (int i = 0; i < this.timehistories.size(); i++) {
            if (this.treeItems.get(i).isSelected()) {                
                double deltaT = this.timehistories.get(i).getDeltaT();
                int size = this.timehistories.get(i).getTimehistory().size();
                ArrayList<Double> timeLine = new ArrayList<>(size);
                for (int k = 0; k < size; k++) {
                    timeLine.add(k * deltaT);
                }
                Pair<ArrayList<Double>, ArrayList<Double>> pair = new Pair<>(timeLine, this.timehistories.get(i).getTimehistory());
                timehistoryLists.add(pair);
            }
        }
    }

    public String getFileName() {
        return this.name;
    }
    
    public void setReferenceToTreeItem(int index, CheckBoxTreeItem obj) {
        this.treeItems.set(index, obj);
    }

    public ArrayList<Timehistory> getTimehistories() {
        return timehistories;
    }  
}
