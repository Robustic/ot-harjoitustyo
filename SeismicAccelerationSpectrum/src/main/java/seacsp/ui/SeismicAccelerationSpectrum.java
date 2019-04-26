package seacsp.ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.beans.value.ObservableValue;
import javafx.scene.chart.LineChart;
import seacsp.logic.Logic;
import seacsp.data.DataCollection;

public class SeismicAccelerationSpectrum extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        TimehistoriesStage timehistoriesStage = new TimehistoriesStage();
        NewDataBaseStage newDataBaseStage = new NewDataBaseStage();
        Logic logic = new Logic();
        
        // Treeview
        TimeHistoryCheckBoxTree timeHistoryCheckBoxTree = new TimeHistoryCheckBoxTree();

        // Buttons
        Button button1 = new Button("Select File");
        FileChooser fileChooserTxt = new FileChooser();
        fileChooserTxt.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        button1.setOnAction(e -> {
            File selectedFile = fileChooserTxt.showOpenDialog(stage);
            DataCollection inputFile = logic.addFileWhenSelectFileButtonPressed(selectedFile);
            if (inputFile != null) {
                timeHistoryCheckBoxTree.addNewFileToTree(inputFile);
            }
        });        
        Button button2 = new Button("Calculate and Draw");
        Button button3 = new Button("Draw timehistories");
        Button button4 = new Button("Add new database");
        Button button6 = new Button("Open database");        
        Button button5 = new Button("Save Timehistories to SQL-db");
        
        // Phii Radio-Buttons
        ToggleGroup toggleGroupPhii = new ToggleGroup();
        RadioButton phii002 = new RadioButton("Damping 0.02");
        phii002.setToggleGroup(toggleGroupPhii);
        RadioButton phii005 = new RadioButton("Damping 0.05");
        phii005.setToggleGroup(toggleGroupPhii);
        phii005.setSelected(true);
        RadioButton freeType = new RadioButton("Damping (free type):");
        freeType.setToggleGroup(toggleGroupPhii);
        freeType.setMinWidth(170);
        freeType.setMaxWidth(170);
        TextField phiiTextField = new TextField("");
        phiiTextField.setEditable(false);
        phiiTextField.setDisable(true);
        phiiTextField.setMinWidth(60);
        phiiTextField.setMaxWidth(60);
        phiiTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String zero = "0";
                char charZero = zero.charAt(0);
                if (!newValue.isEmpty() && (newValue.charAt(0) != charZero || !newValue.matches("\\d{0,1}([\\.]\\d{0,3})?"))) {
                    phiiTextField.setText(oldValue);
                }
            }
        });
        // VBox, Phii Radio-Buttons
        VBox phiiRadioButtonsVBox = new VBox();phiiTextField.setMinHeight(15);        
        phiiRadioButtonsVBox.setSpacing(5);
        phiiRadioButtonsVBox.getChildren().add(phii002);
        phiiRadioButtonsVBox.getChildren().add(phii005);
        // HBox, Select damping value
        HBox freeTypeValue = new HBox();
        freeTypeValue.setMinWidth(230);
        freeTypeValue.setMaxWidth(230);
        freeTypeValue.getChildren().add(freeType);
        freeTypeValue.getChildren().add(phiiTextField);
        phiiRadioButtonsVBox.getChildren().add(freeTypeValue);
        
        toggleGroupPhii.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (toggleGroupPhii.getSelectedToggle() == freeType) {
                phiiTextField.setEditable(true);
                phiiTextField.setDisable(false);
            } else {
                phiiTextField.setEditable(false);                
                phiiTextField.setDisable(true);
            }
        });
        
        // Frequency Radio-Buttons
        ToggleGroup toggleGroupFreq = new ToggleGroup();
        RadioButton freqAsce = new RadioButton("0.1...5 Hz with 0.01 Hz step");
        freqAsce.setToggleGroup(toggleGroupFreq);
        freqAsce.setSelected(true);
        RadioButton freqLinear = new RadioButton("1...50 Hz with 0.1 Hz step");
        freqLinear.setToggleGroup(toggleGroupFreq);
        // VBox, Frequency Radio-Buttons
        VBox freqRadioButtonsVBox = new VBox();
        freqRadioButtonsVBox.setSpacing(5);
        freqRadioButtonsVBox.getChildren().add(freqAsce);
        freqRadioButtonsVBox.getChildren().add(freqLinear);
        
        // HBox, Buttons
        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.getChildren().add(button1);
        buttonsHBox.getChildren().add(button2);
        buttonsHBox.getChildren().add(button3);
        buttonsHBox.getChildren().add(button4);
        buttonsHBox.getChildren().add(button6);
        buttonsHBox.getChildren().add(button5);
        VBox buttonsAndDBNameVBox = new VBox();
        buttonsAndDBNameVBox.setSpacing(10);
        buttonsAndDBNameVBox.getChildren().add(buttonsHBox);
        HBox dbNameHBox = new HBox();
        dbNameHBox.setSpacing(10);
        Label dbNameLabel = new Label("Chosen SQL-database: ");
        dbNameHBox.getChildren().add(dbNameLabel);
        TextField dbNameTextField = new TextField("");
        dbNameTextField.setEditable(false);
        dbNameTextField.setDisable(true);
        dbNameTextField.setMinWidth(250);
        dbNameTextField.setMaxWidth(250);
        dbNameHBox.getChildren().add(dbNameTextField);
        buttonsAndDBNameVBox.getChildren().add(dbNameHBox);
        HBox upperRowHBox = new HBox();
        upperRowHBox.setSpacing(10);
        upperRowHBox.getChildren().add(buttonsAndDBNameVBox);
        upperRowHBox.getChildren().add(phiiRadioButtonsVBox);
        upperRowHBox.getChildren().add(freqRadioButtonsVBox);
        
        // TextArea
        TextArea logTextArea = new TextArea("");
        logic.getLogList().setLogTextArea(logTextArea);
//        logTextArea.setPrefRowCount(3);
        logTextArea.setMinHeight(100);
        logTextArea.setMaxHeight(100);
        logTextArea.setPrefWidth(500);
        logTextArea.setWrapText(true);
        logTextArea.setEditable(false);
        
        // LineChart
        SpectrumLineChart spectrumLineChart = new SpectrumLineChart();
        
        // VBox
        VBox graphLogVBox = new VBox();
        LineChart<Number, Number> lineChart = spectrumLineChart.getEmptySpectrumLineChart();
        graphLogVBox.getChildren().add(lineChart);
        button2.setOnAction(e -> {
            if (phii002.isSelected()) {
                logic.setPhiiValue(0.02);
            } else if (phii005.isSelected()) {
                logic.setPhiiValue(0.05);
            } else if (freeType.isSelected()) {
                logic.setPhiiValue(Double.parseDouble(phiiTextField.getText()));
            }
            if (freqAsce.isSelected()) {
                logic.equalDivisionFrequence(0.1, 5.05, 0.01);
            } else if (freqLinear.isSelected()) {
                logic.equalDivisionFrequence(1, 50.05, 0.1);
            }
            spectrumLineChart.updateSpectrumLineChart(logic.getXYValuesForChart());
        });
        graphLogVBox.getChildren().add(logTextArea);
        
        button3.setOnAction(e -> {
            timehistoriesStage.drawTimehistories(logic.getTimehistoryLists());
        });
        
        button4.setOnAction(e -> {
            if (!newDataBaseStage.isShowing()) {
                newDataBaseStage.showAndWait();
                String givenText = newDataBaseStage.getText();
                if (!givenText.equals("")) {
                    String inputToDBNameTextField = logic.initialize(givenText);                    
                    newDataBaseStage.clearText();
                    if (!inputToDBNameTextField.equals("")) {
                        dbNameTextField.setText(inputToDBNameTextField);
                    }
                }
            }
        });
        
        FileChooser fileChooserDB = new FileChooser();
        fileChooserDB.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("DB Files", "*.db")
        );
        button6.setOnAction(e -> {
            File selectedFile = fileChooserDB.showOpenDialog(stage);
            logic.setDbFile(selectedFile);
            dbNameTextField.setText(selectedFile.getName());
        }); 
        
        button5.setOnAction(e -> {
            logic.saveTimehistoriesToDB();
        });
        
        // Label
        Label infoLabel = new Label("Infotext, not full functionality yet!");
        
        // Layout
        BorderPane layout = new BorderPane();
        layout.setTop(upperRowHBox);
        layout.setLeft(timeHistoryCheckBoxTree.getTreeView());
        layout.setCenter(graphLogVBox);
        layout.setBottom(infoLabel);
        
        // Scene
        Scene scene = new Scene(layout, 1500, 600);
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
