package seacsp.ui;

import javafx.scene.control.TreeItem;
import javafx.scene.control.CheckBoxTreeItem;
import seacsp.filein.InputFile;
import seacsp.filein.InputFiles;

public class CheckBoxTreeItemWithReference<String> extends CheckBoxTreeItem<String> {
    private Object reference;

    public CheckBoxTreeItemWithReference(String str, Object reference) {
        super(str);
        this.reference = reference;
    }
    

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }
}
