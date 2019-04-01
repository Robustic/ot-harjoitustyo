package seacsp.ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        // Buttons
        Button button1 = new Button("Button1");
        Button button2 = new Button("Button2");
        Button button3 = new Button("Button3");
        Button button4 = new Button("Button4");
        
        // HBox, Buttons
        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.getChildren().add(button1);
        buttonsHBox.getChildren().add(button2);
        buttonsHBox.getChildren().add(button3);
        buttonsHBox.getChildren().add(button4);
        
        // Treeview
        TimeHistoryCheckBoxTree timeHistoryCheckBoxTree = new TimeHistoryCheckBoxTree();

        // TextArea
        TextArea logTextArea = new TextArea("Log view has not full functionality yet.\nRow2\nRow3\nRow4\nRow5");
//        logTextArea.setPrefRowCount(3);
        logTextArea.setMinHeight(60);
        logTextArea.setMaxHeight(60);
        logTextArea.setPrefWidth(500);
        logTextArea.setWrapText(true);
        logTextArea.setEditable(false);
        
        // LineChart
        SpectrumLineChart spectrumLineChart = new SpectrumLineChart();
        
        // VBox
        VBox graphLogVBox = new VBox();
        graphLogVBox.getChildren().add(spectrumLineChart.getSpectrumLineChart());
        graphLogVBox.getChildren().add(logTextArea);
        
        // Label
        Label infoLabel = new Label("Infotext, not full functionality yet!");
        
        // Layout
        BorderPane layout = new BorderPane();
        layout.setTop(buttonsHBox);
        layout.setLeft(timeHistoryCheckBoxTree.getTimeHistoryCheckBoxTree());
        layout.setCenter(graphLogVBox);
        layout.setBottom(infoLabel);
        
        // Scene
        Scene scene = new Scene(layout, 1000, 600);
        stage.setTitle("Acceleration Spectrums");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
