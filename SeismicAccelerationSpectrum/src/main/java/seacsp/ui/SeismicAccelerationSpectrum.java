package seacsp.ui;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.value.ObservableValue;
import javafx.scene.chart.LineChart;
import seacsp.data.DataCollection;
import seacsp.logic.Logic;

/**
 * Application.
 */
public class SeismicAccelerationSpectrum extends Application {
    private Stage stage;
    private TimehistoriesStage timehistoriesStage;
    private NewDatabaseStage newDataBaseStage;
    private Logic logic;
    private TimeHistoryCheckBoxTree timeHistoryCheckBoxTree;
    
    private SpectrumLineChart spectrumLineChart;
    
    private Button openTextFileButton;
    private Button calculateSpectrumsButton;
    private Button drawTimehistoriesButton;
    private Button createNewDatabaseButton;
    private Button openDatabaseButton;      
    private Button saveToDatabaseButton;
    private Button exitButton;
    
    private VBox phiiRadioButtonsVBox;
    private VBox freqRadioButtonsVBox;
    
    private RadioButton phii002;
    private RadioButton phii005;
    private RadioButton freeType;
    private TextField phiiTextField;
    
    private RadioButton freqSmallerFrequencies;
    private RadioButton freqLargerFrequencies;
    
    private TextField dbNameTextField;
    
    private void buttons() {        
        FileChooser fileChooserTxt = new FileChooser();
        fileChooserTxt.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        openTextFileButton.setOnAction(e -> {
            File selectedFile = fileChooserTxt.showOpenDialog(stage);
            DataCollection inputFile = logic.addNewDataCollectionFromTextFile(selectedFile);
            if (inputFile != null) {
                timeHistoryCheckBoxTree.addNewFileToTree(inputFile);
            }
        });
        
        calculateSpectrumsButton.setOnAction(e -> {
            if (phii002.isSelected()) {
                logic.setPhiiValue(0.02);
            } else if (phii005.isSelected()) {
                logic.setPhiiValue(0.05);
            } else if (freeType.isSelected()) {
                logic.setPhiiValue(Double.parseDouble(phiiTextField.getText()));
            }
            if (freqSmallerFrequencies.isSelected()) {
                logic.setEqualDivisionFrequence(0.1, 5.0005, 0.01);
            } else if (freqLargerFrequencies.isSelected()) {
                logic.setEqualDivisionFrequence(1, 50.005, 0.1);
            }
            spectrumLineChart.updateSpectrumLineChart(logic.getSpectrumXYValues());
        });        
        
        drawTimehistoriesButton.setOnAction(e -> {
            timehistoriesStage.drawTimehistories(logic.getTimehistoryXYValueLists());
        });        
        
        createNewDatabaseButton.setOnAction(e -> {
            if (!newDataBaseStage.isShowing()) {
                newDataBaseStage.showAndWait();
                String givenText = newDataBaseStage.getText();
                if (!givenText.equals("")) {
                    String inputToDBNameTextField = logic.newDatabaseFile(givenText);                    
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
        openDatabaseButton.setOnAction(e -> {
            File selectedFile = fileChooserDB.showOpenDialog(stage);
            logic.setDatabaseFile(selectedFile);
            dbNameTextField.setText(selectedFile.getName());
//            logic.readDataCollectionsFromDatabase();
            ArrayList<DataCollection> inputFiles = logic.readDataCollectionsFromDatabase();
            if (inputFiles != null) {
                timeHistoryCheckBoxTree.addNewFilesToTree(inputFiles);
            }
        });         
        
        saveToDatabaseButton.setOnAction(e -> {
            logic.saveDataCollectionsToDatabase();
        });
                       
        exitButton.setOnAction(e -> {
            Platform.exit();
        });
    }
    
    private void phiiRadioButtons() {
        // Phii Radio-Buttons
        ToggleGroup toggleGroupPhii = new ToggleGroup();        
        phii002.setToggleGroup(toggleGroupPhii);        
        phii005.setToggleGroup(toggleGroupPhii);
        phii005.setSelected(true);        
        freeType.setToggleGroup(toggleGroupPhii);
        freeType.setMinWidth(170);
        freeType.setMaxWidth(170);        
        phiiTextField.setMinHeight(15); 
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
    }
    
    private void frequencyRadioButtons() {
        // Frequency Radio-Buttons
        ToggleGroup toggleGroupFreq = new ToggleGroup();        
        freqSmallerFrequencies.setToggleGroup(toggleGroupFreq);
        freqSmallerFrequencies.setSelected(true);        
        freqLargerFrequencies.setToggleGroup(toggleGroupFreq);
        // VBox, Frequency Radio-Buttons        
        freqRadioButtonsVBox.setSpacing(5);
        freqRadioButtonsVBox.getChildren().add(freqSmallerFrequencies);
        freqRadioButtonsVBox.getChildren().add(freqLargerFrequencies);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.timehistoriesStage = new TimehistoriesStage();
        this.newDataBaseStage = new NewDatabaseStage();
        this.logic = new Logic();
        
        this.openTextFileButton = new Button("Select and Import Text-file");
        this.calculateSpectrumsButton = new Button("Calculate and Draw Envelope Spectrum");
        this.drawTimehistoriesButton = new Button("Draw Time Histories");
        this.createNewDatabaseButton = new Button("Add New SQL-db");
        this.openDatabaseButton = new Button("Open and Import SQL-db");
        this.saveToDatabaseButton = new Button("Save Timehistories to SQL-db");
        this.exitButton = new Button("Exit"); 

        this.phii002 = new RadioButton("Damping 0.02");
        this.phii005 = new RadioButton("Damping 0.05");
        this.freeType = new RadioButton("Damping (free type):");
        this.phiiTextField = new TextField("");
        this.phiiRadioButtonsVBox = new VBox(); 

        this.freqSmallerFrequencies = new RadioButton("0.1...5 Hz with 0.01 Hz step");
        this.freqLargerFrequencies = new RadioButton("1...50 Hz with 0.1 Hz step");
        this.freqRadioButtonsVBox = new VBox();

        
        // Treeview (Left)
        this.timeHistoryCheckBoxTree = new TimeHistoryCheckBoxTree();
                
        // Upper Row Buttons and RadioButtons etc. (Top)
        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.getChildren().add(openTextFileButton);
        buttonsHBox.getChildren().add(calculateSpectrumsButton);
        buttonsHBox.getChildren().add(drawTimehistoriesButton);
        buttonsHBox.getChildren().add(exitButton);        
        VBox buttonsAndDBNameVBox = new VBox();
        buttonsAndDBNameVBox.setSpacing(10);
        buttonsAndDBNameVBox.getChildren().add(buttonsHBox);
        HBox dbNameHBox = new HBox();
        dbNameHBox.setSpacing(10);  
        dbNameHBox.getChildren().add(createNewDatabaseButton);
        dbNameHBox.getChildren().add(openDatabaseButton);
        dbNameHBox.getChildren().add(saveToDatabaseButton);
        Label dbNameLabel = new Label("Chosen SQL-database: ");
        dbNameHBox.getChildren().add(dbNameLabel);
        this.dbNameTextField = new TextField("");
        dbNameTextField.setEditable(false);
        dbNameTextField.setDisable(true);
        dbNameTextField.setMinWidth(150);
        dbNameTextField.setMaxWidth(150);      
        dbNameHBox.getChildren().add(dbNameTextField);
        buttonsAndDBNameVBox.getChildren().add(dbNameHBox);
        HBox upperRowHBox = new HBox();
        upperRowHBox.setSpacing(10);
        upperRowHBox.getChildren().add(buttonsAndDBNameVBox);
        upperRowHBox.getChildren().add(phiiRadioButtonsVBox);
        upperRowHBox.getChildren().add(freqRadioButtonsVBox);
        
        // VBox (Center) LineChart and LogTextArea
        VBox graphLogVBox = new VBox();
        // LogTextArea
        TextArea logTextArea = new TextArea("");
        logic.getLogList().setLogTextArea(logTextArea);
        logTextArea.setMinHeight(100);
        logTextArea.setMaxHeight(100);
        logTextArea.setPrefWidth(500);
        logTextArea.setWrapText(true);
        logTextArea.setEditable(false);
        // LineChart
        this.spectrumLineChart = new SpectrumLineChart();
        LineChart<Number, Number> lineChart = spectrumLineChart.getEmptySpectrumLineChart();
        graphLogVBox.getChildren().add(lineChart);        
        graphLogVBox.getChildren().add(logTextArea);
        
        // InfoLabel (Bottom)
        Label infoLabel = new Label("Open text-file or open database-file to import data. "
                + "Select time histories to calculate and draw envelope spectrum or "
                + "to draw timehistories. Save timehistories to database-file.");
        
        buttons();
        phiiRadioButtons();
        frequencyRadioButtons();
        
        // Layout
        BorderPane layout = new BorderPane();
        layout.setTop(upperRowHBox);
        layout.setLeft(timeHistoryCheckBoxTree.getTreeView());
        layout.setCenter(graphLogVBox);
        layout.setBottom(infoLabel);
        
        // Scene
        Scene scene = new Scene(layout, 1500, 600);
        stage.setTitle("Seismic Acceleration Spectrum 1.1");
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
