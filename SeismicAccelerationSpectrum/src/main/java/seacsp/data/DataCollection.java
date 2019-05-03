package seacsp.data;

import java.util.ArrayList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.calculations.Timehistory;

/**
 * Class to collect several time histories and spectrums under one collection.
 */
public class DataCollection {
    final private String name;
    final private ArrayList<Timehistory> timehistories;
    final private ArrayList<CheckBoxTreeItem> treeItems;
    private boolean calculated;    
    final private ArrayList<Spectrum> spectrums;

    /**
     * Constructor.
     * 
     * @param   name   name of the data collection
     * 
     * @param   timehistories   time histories as a list
     */
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
    
    /**
     * Method to set "calculated" switch to the position "true".
     */
    public void setCalculatedTrue() {
        this.calculated = true;
    }
    
    /**
     * Method to set "calculated" switch to the position "false".
     */
    public void setCalculatedFalse() {
        this.calculated = false;
    }

    /**
     * Method to check is the "calculated" switch in the position "true".
     * 
     * @return true if object already calculated
     */
    public boolean isCalculated() {
        return this.calculated;
    }   
    
    /**
     * Method to calculate spectrums for the each time history.
     *
     * @param   frequencies   frequency list capsulated inside object
     * 
     * @param   phii   damping value capsulated inside object
     */
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
    
    /**
     * Method to collect spectrums to the list.
     * 
     * @param   spectrumList   list as input where collected spectrums are added
     */
    public void addSpectrumsToList(ArrayList<Spectrum> spectrumList) {
        for (int i = 0; i < this.spectrums.size(); i++) {
            if (this.treeItems.get(i).isSelected()) {
                spectrumList.add(this.spectrums.get(i));
            }
        }
    }
    
    /**
     * Method to collect timeline and time history pairs to the same list.
     *
     * @param   timehistoryLists   list as input where data is added
     */
    public void getTimehistoriesAsTimeAndAccelerationListPairs(ArrayList<Pair<ArrayList<Double>, ArrayList<Double>>> timehistoryLists) {        
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

    /**
     * Method to get name of the data collection.
     * 
     * @return name as a string
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Method to add Timehistory TreeItem object used by user interface.
     *
     * @param   index   index to which Timehistory TreeItem object is added
     *
     * @param   obj   object to set as TreeItem
     */
    public void setReferenceToTreeItemObject(int index, CheckBoxTreeItem obj) {
        this.treeItems.set(index, obj);
    }

    /**
     * Method to get time histories collected to the list.
     * 
     * @return time histories as a list
     */
    public ArrayList<Timehistory> getTimehistories() {
        return this.timehistories;
    }  
}
