package seacsp.data;

import seacsp.file.ReadFile;
import seacsp.file.ReadTextFile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.io.File;
import javafx.scene.control.CheckBoxTreeItem;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;

public class DataCollectionTest {
    private DataCollection dataCollection;
    
    @Before
    public void setUp() {
        File file = new File(System.getProperty("user.dir") + "/" + "TestFile0.txt");
        ReadFile readFile = new ReadFile();
        ReadTextFile readTxtFile = new ReadTextFile(readFile);
        try {
            this.dataCollection = readTxtFile.readTextFileToDataCollection(file);
        } catch (Exception e) {
            System.out.println("");
        }       
    }
    
    @Test
    public void dataFileExist() {        
        assertTrue(this.dataCollection != null);      
    }
        
    @Test
    public void fileNameIsOk() {
        assertEquals("TestFile0.txt", this.dataCollection.getName());
    }
    
    @Test
    public void getTimehistoriesWorks() {
        assertEquals(-0.00038879, this.dataCollection.getTimehistories().get(4).getAccelerationValueInTheIndex(9999), 0.00000001);
    }
    
    @Test
    public void calculatedRightSpectrumValues0() {
        CheckBoxTreeItem obj0 = new CheckBoxTreeItem();
        obj0.setSelected(true);
        CheckBoxTreeItem obj1 = new CheckBoxTreeItem();
        obj1.setSelected(true);
        CheckBoxTreeItem obj2 = new CheckBoxTreeItem();
        obj2.setSelected(true);
        CheckBoxTreeItem obj3 = new CheckBoxTreeItem();
        obj3.setSelected(true);
        CheckBoxTreeItem obj4 = new CheckBoxTreeItem();
        obj4.setSelected(true);        
        this.dataCollection.setReferenceToTreeItemObject(0, obj0);
        this.dataCollection.setReferenceToTreeItemObject(1, obj1);
        this.dataCollection.setReferenceToTreeItemObject(2, obj2);
        this.dataCollection.setReferenceToTreeItemObject(3, obj3);
        this.dataCollection.setReferenceToTreeItemObject(4, obj4);
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.setEqualDivision(3.1, 5.5, 1);
        this.dataCollection.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList;
        spectrumList = new ArrayList<>();
        this.dataCollection.addSpectrumsToList(spectrumList);
        assertEquals(8.79829703, spectrumList.get(0).getAccelerationInTheIndex(0), 0.00000001);
    }
    
    @Test
    public void calculatedRightSpectrumValues4() {
        CheckBoxTreeItem obj0 = new CheckBoxTreeItem();
        obj0.setSelected(true);
        CheckBoxTreeItem obj1 = new CheckBoxTreeItem();
        obj1.setSelected(false);
        CheckBoxTreeItem obj2 = new CheckBoxTreeItem();
        obj2.setSelected(true);
        CheckBoxTreeItem obj3 = new CheckBoxTreeItem();
        obj3.setSelected(true);
        CheckBoxTreeItem obj4 = new CheckBoxTreeItem();
        obj4.setSelected(true);        
        this.dataCollection.setReferenceToTreeItemObject(0, obj0);
        this.dataCollection.setReferenceToTreeItemObject(1, obj1);
        this.dataCollection.setReferenceToTreeItemObject(2, obj2);
        this.dataCollection.setReferenceToTreeItemObject(3, obj3);
        this.dataCollection.setReferenceToTreeItemObject(4, obj4);
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.setEqualDivision(3.1, 5.5, 1);
        this.dataCollection.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList;
        spectrumList = new ArrayList<>();
        this.dataCollection.addSpectrumsToList(spectrumList);
        assertEquals(2.66412234, spectrumList.get(3).getAccelerationInTheIndex(2), 0.00000001);
    }
    
    @Test
    public void notCalculatedWhenAlreadyCalculated0() {
        CheckBoxTreeItem obj0 = new CheckBoxTreeItem();
        obj0.setSelected(true);
        CheckBoxTreeItem obj1 = new CheckBoxTreeItem();
        obj1.setSelected(true);
        CheckBoxTreeItem obj2 = new CheckBoxTreeItem();
        obj2.setSelected(true);
        CheckBoxTreeItem obj3 = new CheckBoxTreeItem();
        obj3.setSelected(true);
        CheckBoxTreeItem obj4 = new CheckBoxTreeItem();
        obj4.setSelected(true);        
        this.dataCollection.setReferenceToTreeItemObject(0, obj0);
        this.dataCollection.setReferenceToTreeItemObject(1, obj1);
        this.dataCollection.setReferenceToTreeItemObject(2, obj2);
        this.dataCollection.setReferenceToTreeItemObject(3, obj3);
        this.dataCollection.setReferenceToTreeItemObject(4, obj4);
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.setEqualDivision(3.1, 5.5, 1);
        this.dataCollection.calculate(frequencies, phii);
        frequencies.setEqualDivision(30.1, 35.5, 1);
        this.dataCollection.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList;
        spectrumList = new ArrayList<>();
        this.dataCollection.addSpectrumsToList(spectrumList);
        assertEquals(8.79829703, spectrumList.get(0).getAccelerationInTheIndex(0), 0.00000001);
    }
    
    @Test
    public void calculatedWhenUpdatedValue0() {
        CheckBoxTreeItem obj0 = new CheckBoxTreeItem();
        obj0.setSelected(true);
        CheckBoxTreeItem obj1 = new CheckBoxTreeItem();
        obj1.setSelected(true);
        CheckBoxTreeItem obj2 = new CheckBoxTreeItem();
        obj2.setSelected(true);
        CheckBoxTreeItem obj3 = new CheckBoxTreeItem();
        obj3.setSelected(true);
        CheckBoxTreeItem obj4 = new CheckBoxTreeItem();
        obj4.setSelected(true);        
        this.dataCollection.setReferenceToTreeItemObject(0, obj0);
        this.dataCollection.setReferenceToTreeItemObject(1, obj1);
        this.dataCollection.setReferenceToTreeItemObject(2, obj2);
        this.dataCollection.setReferenceToTreeItemObject(3, obj3);
        this.dataCollection.setReferenceToTreeItemObject(4, obj4);
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.setEqualDivision(3.1, 5.5, 1);
        this.dataCollection.calculate(frequencies, phii);
        frequencies.setEqualDivision(30.1, 35.5, 1);
        this.dataCollection.setCalculatedFalse();
        this.dataCollection.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList;
        spectrumList = new ArrayList<>();
        this.dataCollection.addSpectrumsToList(spectrumList);
        assertEquals(1.01721305, spectrumList.get(0).getAccelerationInTheIndex(0), 0.00000001);
    }
    
    @Test
    public void nameIsRight() {
        assertEquals("TestFile0.txt", this.dataCollection.getName());
    }
}
