package seacsp.logic;

import java.util.ArrayList;
import javafx.scene.control.TextArea;

/**
 * Class to collect logs.
 */
public class LogList {
    final private ArrayList<String> log;
    private TextArea logTextArea;

    /**
     * Constructor.
     */
    public LogList() {        
        this.log = new ArrayList<>();
    }

    /**
     * Method to set LogTextArea.
     *
     * @param   logTextArea   TextArea
     */
    public void setLogTextArea(TextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    /**
     * Method to get log strings as list.
     *
     * @return log strings as list
     */
    public ArrayList<String> getLogMessagesAsStringList() {
        return log;
    }

    /**
     * Method to add new log.
     *
     * @param   newLog   new log
     */
    public void addNewLogMessage(String newLog) {
        this.log.add(newLog);
        if (logTextArea != null) {
            this.logTextArea.appendText("\n" + newLog);
            this.logTextArea.setScrollTop(Double.MAX_VALUE);
        }
    }
}
