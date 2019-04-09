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
import javafx.util.Pair;
import java.time.temporal.ValueRange;

public class SpectrumLineChart {
    private double min;
    private double max;
    LineChart<Number, Number> spectrumLineChart;
    NumberAxis xAxel;
    NumberAxis yAxel;
    
    public LineChart<Number, Number> getEmptySpectrumLineChart() {
        XYChart.Series<Number, Number> accData = new XYChart.Series<>();
        accData.setName("Spectrum");
        accData.getData().add(new XYChart.Data<>(0, 0));
        this.min = 0;
        this.max = 1;
        newLineChart(accData);
        return this.spectrumLineChart;
    }
    
    public void newLineChart(XYChart.Series<Number, Number> accData) {
        this.xAxel = initializeXAxel();
        this.yAxel = initializeYAxel();
        this.spectrumLineChart = new LineChart<>(this.xAxel, this.yAxel);
        this.spectrumLineChart.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        this.spectrumLineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");         
        this.spectrumLineChart.setTitle("Envelope Spectrum");
        this.spectrumLineChart.setCreateSymbols(false);
        this.spectrumLineChart.setLegendVisible(false);
        this.spectrumLineChart.setPrefHeight(1200);
        this.spectrumLineChart.getData().add(accData);
    }
    
    public void updateSpectrumLineChart(ArrayList<Pair<Double, Double>> pairs) {
        XYChart.Series<Number, Number> accData = new XYChart.Series<>();
        accData.setName("Spectrum");
        addAccData(accData, pairs);
        this.min = pairs.get(0).getKey();
        this.max = pairs.get(pairs.size() - 1).getKey();
        this.xAxel.setLowerBound(this.min);
        this.xAxel.setUpperBound(this.max);
        this.spectrumLineChart.getData().clear();
        this.spectrumLineChart.getData().add(accData);
    }
    
    public void addAccData(XYChart.Series<Number, Number> accData, ArrayList<Pair<Double, Double>> pairs) {
        for (int i = 0; i < pairs.size(); i++) {
            accData.getData().add(new XYChart.Data<>(pairs.get(i).getKey(), pairs.get(i).getValue()));
        }  
    }
    
    public NumberAxis initializeXAxel() {
        this.xAxel = new NumberAxis(this.min, this.max, 10);
        this.xAxel.setTickLabelFormatter(new StringConverter<Number>() {
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
        this.xAxel.setLabel("Frequency [Hz]");
        return this.xAxel;
    }
    
    public NumberAxis initializeYAxel() {
        this.yAxel = new NumberAxis();        
        this.yAxel.setLabel("Acceleration [m/s²]");
        return this.yAxel;
    }
    
//    public void initializeAccData(XYChart.Series<Number, Number> accData) {
//        Phii phii = new Phii(0.05);
//        Frequencies frequencies = new Frequencies();
//        frequencies.equalDivision(1, 50.05, 0.1);
//        ArrayList<Double> timehistoryList = new ArrayList<>();
//        for (int i = 0; i <= 1100; i++) {
//            timehistoryList.add(Math.sin(2 * 3.14 * 15 * i * 0.01) + Math.sin(0.8 * 2 * 3.14 * 26 * (i * 0.01 - 1)));
//        }
//        Timehistory timehistory = new Timehistory(timehistoryList, 0.01, "");
//        Spectrum spectrum = new Spectrum();
//        spectrum.calculateSpectrum(frequencies, timehistory, phii);
//        ArrayList<Double> frequencyList = frequencies.getFrequences();
//        this.min = frequencyList.get(0);
//        this.max = frequencyList.get(frequencyList.size() - 1);
//        ArrayList<Double> accelerationList = spectrum.getAccelerationList();
//        for (int i = 0; i < frequencyList.size(); i++) {
//            accData.getData().add(new XYChart.Data<>(frequencyList.get(i), accelerationList.get(i)));
//        }
//    }
}
