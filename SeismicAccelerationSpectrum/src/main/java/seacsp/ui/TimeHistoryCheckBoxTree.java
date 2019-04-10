package seacsp.ui;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import seacsp.data.DataFile;
import seacsp.calculations.Timehistory;

public class TimeHistoryCheckBoxTree {    
    TreeView<String> treeView;
    CheckBoxTreeItem<String> all;
    
    public TimeHistoryCheckBoxTree() {
        this.treeView = new TreeView<>();
        this.all = new CheckBoxTreeItem<>("All");
        this.treeView.setRoot(this.all);
        
        // set the cell factory
        this.treeView.setCellFactory(CheckBoxTreeCell.forTreeView());
    }

    public TreeView<String> getTreeView() {
        return treeView;
    }  
    
    public TreeView<String> addNewFileToTree(DataFile inputFile) {
        CheckBoxTreeItem<String> file = new CheckBoxTreeItem<>(inputFile.getFileName());
        for (int i = 0; i < inputFile.getTimehistories().size(); i++) {
            Timehistory timehistory = inputFile.getTimehistories().get(i);
            CheckBoxTreeItem<String> history = new CheckBoxTreeItem<>(timehistory.getName());
            file.getChildren().add(history);
            inputFile.setReferenceToTreeItem(i, history);
        }
        file.setExpanded(true);
        this.all.setExpanded(true);
        this.all.getChildren().add(file);        
        return this.treeView;
    }
}
