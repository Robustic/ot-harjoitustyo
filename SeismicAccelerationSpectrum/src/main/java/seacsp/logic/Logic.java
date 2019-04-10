package seacsp.logic;

import java.io.File;
import javafx.scene.chart.LineChart;
import seacsp.calculations.Frequencies;
import seacsp.data.DataFile;
import seacsp.data.DataFiles;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import java.util.*;
import javafx.util.Pair;

public class Logic {
    private DataFiles inputFiles;
    private Phii phii;
    private Frequencies frequencies;

    public Logic() {
        this.inputFiles = new DataFiles();
        this.phii = new Phii(0.05);
        this.frequencies = new Frequencies();
        frequencies.equalDivision(1, 50.05, 0.1);
    }
    
    public DataFile addFileWhenSelectFileButtonPressed(File file) {
        try {
            return this.inputFiles.addFile(file);            
        } catch (Exception e) {
            System.out.println("File " + file.getName() + " could not be read.");
            System.out.println(e);
            return null;
        }
    }
    
    public ArrayList<Pair<Double, Double>> getXYValuesForChart() {
        this.inputFiles.calculate(this.frequencies, this.phii);
        ArrayList<Spectrum> spectrumList = new ArrayList<Spectrum>();
        this.inputFiles.getSpectrumList(spectrumList);                
        ArrayList<Pair<Double, Double>> pairs = new ArrayList<>();
        for (int i = 0; i < this.frequencies.numberOfFrequencies(); i++) {
            double max = 0;
            for (Spectrum spectrum : spectrumList) {
                if (spectrum.getAccWithFrequency(i) > max) {
                    max = spectrum.getAccWithFrequency(i);
                }
            }
            pairs.add(new Pair<>(this.frequencies.getFrequency(i), max));
        }
        return pairs;
    }
    
}
