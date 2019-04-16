package seacsp.ui;

import java.util.ArrayList;
import java.util.Collection;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javafx.util.StringConverter;

public class TimehistoryLineChart {
    private double min;
    private double max;
    LineChart<Number, Number> timehistoryLineChart;
    NumberAxis xAxel;
    NumberAxis yAxel;

    public TimehistoryLineChart() {
        this.xAxel = initializeXAxel();
        this.yAxel = initializeYAxel();
        this.timehistoryLineChart = new LineChart<>(this.xAxel, this.yAxel);
        this.timehistoryLineChart.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        this.timehistoryLineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");         
        this.timehistoryLineChart.setTitle("Selected timehistories");
        this.timehistoryLineChart.setCreateSymbols(false);
        this.timehistoryLineChart.setLegendVisible(false);
        this.timehistoryLineChart.setPrefHeight(1200);
        this.timehistoryLineChart.setAnimated(false);
    }

    public LineChart<Number, Number> getSpectrumLineChart() {
        return timehistoryLineChart;
    }
    
    public LineChart<Number, Number> getEmptySpectrumLineChart() {
        XYChart.Series<Number, Number> accData = new XYChart.Series<>();
        accData.getData().add(new XYChart.Data<>(0, 0));
        this.min = 0;
        this.max = 1;
        this.timehistoryLineChart.getData().add(accData);
        return this.timehistoryLineChart;
    }
    
    public void updateTimehistoryLineChart(ArrayList<ArrayList<Pair<Double, Double>>> pairLists) {
        this.timehistoryLineChart.getData().clear();
        if (pairLists.size() < 0) {
            return;
        }
        this.min = pairLists.get(0).get(0).getKey();
        this.max = pairLists.get(0).get(pairLists.get(0).size() - 1).getKey();
        for (ArrayList<Pair<Double, Double>> pairs : pairLists) {
            XYChart.Series<Number, Number> accData = new XYChart.Series<>();
            addAccData(accData, pairs);
            if (this.min > pairs.get(0).getKey()) {
                this.min = pairs.get(0).getKey();
            }
            if (this.max < pairs.get(pairs.size() - 1).getKey()) {
                this.max = pairs.get(pairs.size() - 1).getKey();
            }   
            this.timehistoryLineChart.getData().add(accData);
        }
        this.xAxel.setLowerBound(this.min);
        this.xAxel.setUpperBound(this.max);
    }
    
    public void addAccData(XYChart.Series<Number, Number> accData, ArrayList<Pair<Double, Double>> pairs) {   
        Collection<XYChart.Data<Number, Number>> samples = new ArrayList<>(pairs.size());
        for (int i = 0; i < pairs.size(); i++) {
            XYChart.Data<Number, Number> sample = new XYChart.Data<>((Number) pairs.get(i).getKey(), (Number) pairs.get(i).getValue());
            samples.add(sample);
        }
        accData.getData().addAll(samples);
    }
    
    public NumberAxis initializeXAxel() {
        this.xAxel = new NumberAxis(this.min, this.max, 1);
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
        this.xAxel.setLabel("Time [s]");
        return this.xAxel;
    }
    
    public NumberAxis initializeYAxel() {
        this.yAxel = new NumberAxis();        
        this.yAxel.setLabel("Acceleration [m/s²]");
        return this.yAxel;
    }
}
