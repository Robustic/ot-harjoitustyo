package seacsp.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import seacsp.calculations.Timehistory;
import seacsp.calculations.Spectrum;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import javafx.scene.control.CheckBoxTreeItem;

public class DataFile {
    private File file;
    private ArrayList<Timehistory> timehistories;
    private ArrayList<CheckBoxTreeItem> treeItems;
    private ArrayList<Boolean> checked;    
    private ArrayList<Spectrum> spectrums;

//    public DataFile(File file) {
//        this.file = file;
//        this.timehistories = new ArrayList<>();
//        this.treeItems = new ArrayList<>();
//        this.checked = new ArrayList<>();
//        this.spectrums = new ArrayList<>();
//    }

    public DataFile(File file, ArrayList<Timehistory> timehistories) {
        this.file = file;
        this.timehistories = timehistories;
        this.treeItems = new ArrayList<>();
        this.checked = new ArrayList<>();
        this.spectrums = new ArrayList<>();
    }
    
    
    
    public void calculate(Frequencies frequencies, Phii phii) {
        this.spectrums.clear();
        for (int i = 0; i < this.timehistories.size(); i++) {
            this.spectrums.add(new Spectrum());
            this.spectrums.get(i).calculateSpectrum(frequencies, this.timehistories.get(i), phii);
        }
    }
    
    public void addSpectrumsToList(ArrayList<Spectrum> spectrumList) {
        for (int i = 0; i < this.spectrums.size(); i++) {
            if (this.treeItems.get(i).isSelected()) {
                spectrumList.add(this.spectrums.get(i));
            }
        }
    }

    public String getFileName() {
        return file.getName();
    }
    
    public void setReferenceToTreeItem(int index, CheckBoxTreeItem obj) {
        while (this.treeItems.size() < this.timehistories.size()) {
            this.treeItems.add(null);
        }
        this.treeItems.set(index, obj);
    }

    public ArrayList<Timehistory> getTimehistories() {
        return timehistories;
    }
    
//    public void stringToData(String fileAsString) throws Exception {        
//        try {
//            readStringLineByLine(fileAsString);
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//    
//    public ArrayList<String> cleanColumns(String[] columnsIn) {
//        ArrayList<String> columnsOut = new ArrayList<>();
//        for (int i = 0; i < columnsIn.length; i++) {
//            columnsIn[i] = columnsIn[i].replaceAll("\\s+", "");
//            if (!columnsIn[i].equals("") && !columnsIn[i].equals(" ")) {
//                columnsOut.add(columnsIn[i]);
//            }
//        }        
//        return columnsOut;
//    }
//    
//    public void addHeaders(String fileAsString, Scanner scanner) throws Exception {
//        boolean findHeaders = true;
//        while (scanner.hasNextLine() && findHeaders) {
//            String line = scanner.nextLine();
//            ArrayList<String> columns = cleanColumns(line.split(" "));            
//            if (columns.size() > 1) {
//                for (int i = 1; i < columns.size(); i++) {
//                    timehistories.add(new Timehistory(columns.get(i)));                    
//                }
//                findHeaders = false;
//            }        
//        }
//        if (timehistories.size() < 1) {
//            throw new IllegalArgumentException("File " + file.getName() + " contains ineligible data. No headers.");
//        }        
//    }
//    
//    public void addTimehistories(String fileAsString, Scanner scanner) throws Exception {
//        ArrayList<Double> time = new ArrayList<>();
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            ArrayList<String> columns = cleanColumns(line.split(" "));            
//            if (columns.size() > 1) {
//                addColumnContent(columns, time);
//            }        
//        }
//        if (timehistories.size() < 1) {
//            throw new IllegalArgumentException("File " + file.getName() + " contains ineligible data.");
//        }
//        addDeltaTValues(time);
//    }
//    
//    public void addColumnContent(ArrayList<String> columns, ArrayList<Double> time) throws Exception {
//        if (columns.size() - 1 != timehistories.size()) {
//            throw new IllegalArgumentException("File " + file.getName() + " contains ineligible data. Number of columns do not match.");
//        }                
//        for (int i = 0; i < columns.size(); i++) {
//            if (!isNumeric(columns.get(i))) {
//                throw new NumberFormatException("File " + file.getName() + " contains ineligible data. Numbers contains bad data.");
//            }
//            if (i == 0) {
//                time.add(Double.parseDouble(columns.get(0)));
//            } else {
//                this.timehistories.get(i - 1).addNewAcc(Double.parseDouble(columns.get(i)));          
//            }
//        }
//    }
//    
//    public void addDeltaTValues(ArrayList<Double> time) throws Exception {
//        Double deltaT = (time.get(time.size() - 1) - time.get(0)) / (time.size() - 1);
//        Double marginal = 0.000001;
//        for (int i = 0; i < time.size(); i++) {
//            Double tick = i * deltaT + time.get(0);
//            if (time.get(i) < tick - marginal || time.get(i) > tick + marginal) {
//                throw new IllegalArgumentException("In file " + file.getName() + " time is not increasing linerially.");
//            }
//        }
//        for (Timehistory timehistory : timehistories) {
//            timehistory.setDeltaT(deltaT);
//        }
//    }    
//    
//    public static boolean isNumeric(String string) { 
//        try {  
//            Double.parseDouble(string);  
//            return true;
//        } catch (NumberFormatException e) {  
//            return false;  
//        }  
//    }
//    
//    public void readStringLineByLine(String fileAsString) throws Exception {        
//        Scanner scanner = new Scanner(fileAsString);
//        addHeaders(fileAsString, scanner);
//        addTimehistories(fileAsString, scanner);        
//    }
}
