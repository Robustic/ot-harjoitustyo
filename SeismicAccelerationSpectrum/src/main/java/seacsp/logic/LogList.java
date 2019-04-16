package seacsp.logic;

import java.util.ArrayList;
import javafx.scene.control.TextArea;

public class LogList {
    private ArrayList<String> log;
    TextArea logTextArea;

    public LogList() {        
        this.log = new ArrayList<>();
    }

    public void setLogTextArea(TextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void addLog(String newLog) {
        this.log.add(newLog);
        if (logTextArea != null) {
            this.logTextArea.appendText("\n" + newLog);
            this.logTextArea.setScrollTop(Double.MAX_VALUE);
        }
    }
}
