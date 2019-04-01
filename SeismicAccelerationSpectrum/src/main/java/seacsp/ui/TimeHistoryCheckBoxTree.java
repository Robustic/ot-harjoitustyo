package seacsp.ui;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;


public class TimeHistoryCheckBoxTree {
    
    public TreeView<String> getTimeHistoryCheckBoxTree() {
        // Create the tree model
        CheckBoxTreeItem<String> timeHistory11 = new CheckBoxTreeItem<>("TimeHistory1");
        CheckBoxTreeItem<String> timeHistory12 = new CheckBoxTreeItem<>("TimeHistory2");
        CheckBoxTreeItem<String> timeHistory13 = new CheckBoxTreeItem<>("TimeHistory3");
        CheckBoxTreeItem<String> timeHistory14 = new CheckBoxTreeItem<>("TimeHistory4");
        CheckBoxTreeItem<String> timeHistory15 = new CheckBoxTreeItem<>("TimeHistory5");

        CheckBoxTreeItem<String> file1 = new CheckBoxTreeItem<>("File1.his");
        file1.setExpanded(true);
        file1.getChildren().add(timeHistory11);
        file1.getChildren().add(timeHistory12);
        file1.getChildren().add(timeHistory13);
        file1.getChildren().add(timeHistory14);
        file1.getChildren().add(timeHistory15);
        
        CheckBoxTreeItem<String> timeHistory21 = new CheckBoxTreeItem<>("TimeHistory1");
        CheckBoxTreeItem<String> timeHistory22 = new CheckBoxTreeItem<>("TimeHistory2");
        CheckBoxTreeItem<String> timeHistory23 = new CheckBoxTreeItem<>("TimeHistory3");
        CheckBoxTreeItem<String> timeHistory24 = new CheckBoxTreeItem<>("TimeHistory4");

        CheckBoxTreeItem<String> file2 = new CheckBoxTreeItem<>("File2.his");
        file2.setExpanded(true);
        file2.getChildren().add(timeHistory21);
        file2.getChildren().add(timeHistory22);
        file2.getChildren().add(timeHistory23);
        file2.getChildren().add(timeHistory24);
        
        CheckBoxTreeItem<String> all = new CheckBoxTreeItem<>("All");
        all.setExpanded(true);
        all.getChildren().add(file1);
        all.getChildren().add(file2);

        // create the treeView
        final TreeView<String> treeView = new TreeView<>();
        treeView.setRoot(all);
        
        // set the cell factory
        treeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        
        return treeView;
    }
}
