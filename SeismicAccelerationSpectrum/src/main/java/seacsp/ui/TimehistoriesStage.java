package seacsp.ui;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class TimehistoriesStage extends Stage {
    final private TimehistoryLineChart timehistoryLineCharts;
    private Scene secondScene;
    
    public TimehistoriesStage() {
        this.setTitle("Timehistories");
        this.timehistoryLineCharts = new TimehistoryLineChart();        
    }
    
    public void drawTimehistories(ArrayList<ArrayList<Pair<Double, Double>>> timehistoryXYValuesForChart) {
        if (timehistoryXYValuesForChart.size() == 0) {
            timehistoryXYValuesForChart = new ArrayList<>();        
            ArrayList<Pair<Double, Double>> pairList = new ArrayList<>();
            pairList.add(new Pair<>(0.0, 0.0));
            timehistoryXYValuesForChart.add(pairList);
        } else {        
            this.timehistoryLineCharts.updateTimehistoryLineChart(timehistoryXYValuesForChart);
        }
        if (this.secondScene == null) {
            this.secondScene = new Scene(this.timehistoryLineCharts.getSpectrumLineChart(), 1200, 400);
            this.setScene(this.secondScene);
        }
        this.show();
    }
}
