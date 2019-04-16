package seacsp.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;
import seacsp.calculations.Timehistory;

public class ReadTxtFile {
    final private File file;
    final private ArrayList<Timehistory> timehistories;

    public ReadTxtFile(File file) {
        this.file = file;
        this.timehistories = new ArrayList<>();
    } 
    
    public DataFile readTxtFile() throws Exception {
        String fileAsString = readFileLineByLine();        
        stringToData(fileAsString);        
        return new DataFile(this.file, this.timehistories);        
    }
    
    public String readFileLineByLine() throws Exception {
        StringBuilder contentBuilder = new StringBuilder(); 
        try (Stream<String> stream = Files.lines(Paths.get(this.file.toString()), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) {
            throw e;
        } 
        return contentBuilder.toString();
    }
    
    public void stringToData(String fileAsString) throws Exception {        
        Scanner scanner = new Scanner(fileAsString);
        addHeaders(scanner);
        addTimehistories(fileAsString, scanner); 
    }
    
    public ArrayList<String> cleanColumns(String[] columnsIn) {
        ArrayList<String> columnsOut = new ArrayList<>();
        for (int i = 0; i < columnsIn.length; i++) {
            columnsIn[i] = columnsIn[i].replaceAll("\\s+", "");
            if (!columnsIn[i].equals("")) {
                columnsOut.add(columnsIn[i]);
            }
        }        
        return columnsOut;
    }
    
    public void addHeaders(Scanner scanner) throws Exception {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<String> columns = cleanColumns(line.split(" "));            
            if (columns.size() > 1) {
                for (int i = 1; i < columns.size(); i++) {
                    this.timehistories.add(new Timehistory(columns.get(i)));                    
                }
                break;
            }        
        }
        if (this.timehistories.size() < 1) {
            throw new IllegalArgumentException("File " + file.getName() + " contains ineligible data. No headers.");
        }        
    }
    
    public void addTimehistories(String fileAsString, Scanner scanner) throws Exception {
        ArrayList<Double> timeLine = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<String> columns = cleanColumns(line.split(" "));            
            if (columns.size() > 1) {
                addColumnsContent(columns, timeLine);
            }        
        }
        addDeltaTValues(timeLine);
    }
    
    public void addColumnsContent(ArrayList<String> columns, ArrayList<Double> timeLine) throws Exception {
        if (columns.size() - 1 != this.timehistories.size()) {
            throw new IllegalArgumentException("File " + this.file.getName() + " contains ineligible data. Number of columns do not match.");
        }                
        for (int i = 0; i < columns.size(); i++) {
            if (!isNumeric(columns.get(i))) {
                throw new NumberFormatException("File " + this.file.getName() + " contains ineligible data. Numbers contains bad data.");
            }
            if (i == 0) {
                timeLine.add(Double.parseDouble(columns.get(0)));
            } else {
                this.timehistories.get(i - 1).addNewAcc(Double.parseDouble(columns.get(i)));          
            }
        }
    }
    
    public void addDeltaTValues(ArrayList<Double> timeLine) throws Exception {
        if (timeLine.size() < 2) {
            throw new IllegalArgumentException("File " + this.file.getName() + " contains ineligible data. Less than 2 data rows.");
        }
        if (timeLine.get(0) >= timeLine.get(timeLine.size() - 1)) {
            throw new IllegalArgumentException("File " + this.file.getName() + " time is decreasing.");
        }
        Double deltaT = (timeLine.get(timeLine.size() - 1) - timeLine.get(0)) / (timeLine.size() - 1);
        Double marginal = 0.000001;
        for (int i = 0; i < timeLine.size(); i++) {
            Double tick = i * deltaT + timeLine.get(0);
            if (timeLine.get(i) < (tick - marginal) || timeLine.get(i) > (tick + marginal)) {
                throw new IllegalArgumentException("In file " + this.file.getName() + " time is not increasing linerially.");
            }
        }
        for (Timehistory timehistory : this.timehistories) {
            timehistory.setDeltaT(deltaT);
        }
    }    
    
    public static boolean isNumeric(String string) { 
        try {  
            Double.parseDouble(string);  
            return true;
        } catch (NumberFormatException e) {  
            return false;  
        }  
    }
}
