package seacsp.ui;

import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class NewDataBaseStage extends Stage {
    private String text;
    
    public NewDataBaseStage() {
        this.setTitle("Add new database");
        this.setAlwaysOnTop(true);
        initializeNewDataBaseStage();
        this.text = "";
    }

    public String getText() {
        return this.text;
    }

    public void clearText() {
        this.text = "";
    }
    
    private void initializeNewDataBaseStage() {
        TextField dbName = new TextField("");
        dbName.setMinWidth(250);
        dbName.setMaxWidth(250);
        dbName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty() && !Pattern.matches("[a-zA-Z]+", newValue)) {
                    dbName.setText(oldValue);
                }
            }
        });
        Button ok = new Button("Ok");
        ok.setOnAction(e -> {
            if (dbName.getText().equals("")) {
                this.text = "-1";
            } else {
                this.text = dbName.getText();
            }            
            dbName.clear();
            this.close();
        });
        Button cancel = new Button("Cancel");        
        cancel.setOnAction(e -> {
            this.text = "";
            dbName.clear();            
            this.close();
        });
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().add(ok);
        buttons.getChildren().add(cancel);
        VBox textFieldAndButtons = new VBox();
        textFieldAndButtons.setSpacing(10);
        textFieldAndButtons.getChildren().add(dbName);
        textFieldAndButtons.getChildren().add(buttons);
        Scene scene = new Scene(textFieldAndButtons, 300, 80);
        this.setScene(scene);
    }
}
