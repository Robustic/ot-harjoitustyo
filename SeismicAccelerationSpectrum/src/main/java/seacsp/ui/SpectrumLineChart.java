package seacsp.ui;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;
import seacsp.calculations.Timehistory;


public class SpectrumLineChart {
    private double min;
    private double max;
    
    public LineChart<Number, Number> getSpectrumLineChart() {
        XYChart.Series<Number, Number> accData = new XYChart.Series<>();
        accData.setName("Spectrum");
        initializeAccData(accData);
        
        LineChart<Number, Number> spectrumLineChart = new LineChart<>(initializeXAxel(), initializeYAxel());
        spectrumLineChart.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        spectrumLineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
         
        spectrumLineChart.setTitle("Envelope Spectrum");
        spectrumLineChart.setCreateSymbols(false);
        spectrumLineChart.setLegendVisible(false);
        spectrumLineChart.setPrefHeight(1200);

        spectrumLineChart.getData().add(accData);
        return spectrumLineChart;
    }
    
    public NumberAxis initializeXAxel() {
        NumberAxis xAxel = new NumberAxis(this.min, this.max, 4);
        xAxel.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return "" + (object.doubleValue());
            }

            @Override
            public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val;
            }
        });
        xAxel.setLabel("Frequency [Hz]");
        return xAxel;
    }
    
    public NumberAxis initializeYAxel() {
        NumberAxis yAxel = new NumberAxis();        
        yAxel.setLabel("Acceleration [m/s²]");
        return yAxel;
    }
    
    public void initializeAccData(XYChart.Series<Number, Number> accData) {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(1, 50.05, 0.1);
        ArrayList<Double> timehistoryList = new ArrayList<>();
        for (int i = 0; i <= 1100; i++) {
            timehistoryList.add(Math.sin(2 * 3.14 * 15 * i * 0.01) + Math.sin(0.8 * 2 * 3.14 * 26 * (i * 0.01 - 1)));
        }
        Timehistory timehistory = new Timehistory(timehistoryList, 0.01, "", "");
        Spectrum spectrum = new Spectrum(frequencies, timehistory, phii);
        spectrum.calculateSpectrum();
        ArrayList<Double> frequencyList = frequencies.getFrequences();
        this.min = frequencyList.get(0);
        this.max = frequencyList.get(frequencyList.size() - 1);
        ArrayList<Double> accelerationList = spectrum.getAccelerationList();
        for (int i = 0; i < frequencyList.size(); i++) {
            accData.getData().add(new XYChart.Data<>(frequencyList.get(i), accelerationList.get(i)));
        }
    }
}
