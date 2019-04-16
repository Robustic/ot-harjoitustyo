package seacsp.data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.io.File;
import javafx.scene.control.CheckBoxTreeItem;
import seacsp.calculations.Frequencies;
import seacsp.calculations.Phii;
import seacsp.calculations.Spectrum;

public class DataFileTest {
    private DataFile dataFile;
    
    @Before
    public void setUp() {
        File file = new File(System.getProperty("user.dir") + "/" + "TestFile0.txt");
        ReadTxtFile readTxtFile = new ReadTxtFile(file);
        try {
            this.dataFile = readTxtFile.readTxtFile();
        } catch (Exception e) {
            System.out.println("");
        }       
    }
    
    @Test
    public void dataFileExist() {        
        assertTrue(this.dataFile != null);      
    }
        
    @Test
    public void fileNameIsOk() {
        assertEquals("TestFile0.txt", this.dataFile.getFileName());
    }
    
    @Test
    public void getTimehistoriesWorks() {
        assertEquals(-0.00038879, this.dataFile.getTimehistories().get(4).getAcc(9999), 0.00000001);
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
        this.dataFile.setReferenceToTreeItem(0, obj0);
        this.dataFile.setReferenceToTreeItem(1, obj1);
        this.dataFile.setReferenceToTreeItem(2, obj2);
        this.dataFile.setReferenceToTreeItem(3, obj3);
        this.dataFile.setReferenceToTreeItem(4, obj4);
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataFile.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList;
        spectrumList = new ArrayList<>();
        this.dataFile.addSpectrumsToList(spectrumList);
        assertEquals(8.79829703, spectrumList.get(0).getAccWithFrequency(0), 0.00000001);
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
        this.dataFile.setReferenceToTreeItem(0, obj0);
        this.dataFile.setReferenceToTreeItem(1, obj1);
        this.dataFile.setReferenceToTreeItem(2, obj2);
        this.dataFile.setReferenceToTreeItem(3, obj3);
        this.dataFile.setReferenceToTreeItem(4, obj4);
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataFile.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList;
        spectrumList = new ArrayList<>();
        this.dataFile.addSpectrumsToList(spectrumList);
        assertEquals(2.66412234, spectrumList.get(3).getAccWithFrequency(2), 0.00000001);
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
        this.dataFile.setReferenceToTreeItem(0, obj0);
        this.dataFile.setReferenceToTreeItem(1, obj1);
        this.dataFile.setReferenceToTreeItem(2, obj2);
        this.dataFile.setReferenceToTreeItem(3, obj3);
        this.dataFile.setReferenceToTreeItem(4, obj4);
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataFile.calculate(frequencies, phii);
        frequencies.equalDivision(30.1, 35.5, 1);
        this.dataFile.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList;
        spectrumList = new ArrayList<>();
        this.dataFile.addSpectrumsToList(spectrumList);
        assertEquals(8.79829703, spectrumList.get(0).getAccWithFrequency(0), 0.00000001);
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
        this.dataFile.setReferenceToTreeItem(0, obj0);
        this.dataFile.setReferenceToTreeItem(1, obj1);
        this.dataFile.setReferenceToTreeItem(2, obj2);
        this.dataFile.setReferenceToTreeItem(3, obj3);
        this.dataFile.setReferenceToTreeItem(4, obj4);
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataFile.calculate(frequencies, phii);
        frequencies.equalDivision(30.1, 35.5, 1);
        this.dataFile.setCalculatedFalse();
        this.dataFile.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList;
        spectrumList = new ArrayList<>();
        this.dataFile.addSpectrumsToList(spectrumList);
        assertEquals(1.01721305, spectrumList.get(0).getAccWithFrequency(0), 0.00000001);
    }
}
