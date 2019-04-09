package seacsp.filein;

import java.io.File;
import java.util.ArrayList;
import seacsp.calculations.Timehistory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javafx.util.Pair;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;

public class InputFiles {
    private ArrayList<InputFile> inputFileList;
    private ArrayList<Boolean> checked;
    private boolean calculated;

    public InputFiles() {
        this.inputFileList = new ArrayList<>();
        this.checked = new ArrayList<>();
        calculated = false;
    }
    
    public void getXYValuesForChart() {
        ArrayList<Pair<Double, Double>> pairs = new ArrayList<>();
        
    }
    
    public void calculate(Frequencies frequencies, Phii phii) {
        if (!this.calculated) {
            for (InputFile inputFile : this.inputFileList) {
                inputFile.calculate(frequencies, phii);
            }
            this.calculated = true;
        }
    }
    
    public void getSpectrumList(ArrayList<Spectrum> spectrumList) {
        for (InputFile inputFile : inputFileList) {
            inputFile.addSpectrumsToList(spectrumList);
        }
    }
    
    public InputFile addFile(File file) throws Exception {
        String fileName =  file.toString();
        String fileAsString;
        if (fileAlreadyAdded(file)) {
            throw new IllegalArgumentException("File " + file.getName() + " already added.");
        }
        try {
            fileAsString = readFileLineByLine(fileName);
        } catch (Exception e) {
            throw e;
        }
        InputFile inputFile = new InputFile(file);
        try {
            inputFile.stringToData(fileAsString);
        } catch (Exception e) {
            throw e;
        }        
        this.inputFileList.add(inputFile);
        return inputFile;
    }
    
    public boolean fileAlreadyAdded(File file) {
        String fileName = file.getName();
        for (InputFile inputFile : this.inputFileList) {
            if (fileName.equals(inputFile.getFileName())) {
                return true;
            }
        }        
        return false;
    }
    
    public String readFileLineByLine(String filePath) throws Exception {
        StringBuilder contentBuilder = new StringBuilder(); 
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) {
//            System.out.println("*** error when reading file " + filePath);
            throw e;
        } 
        return contentBuilder.toString();
    }
}
