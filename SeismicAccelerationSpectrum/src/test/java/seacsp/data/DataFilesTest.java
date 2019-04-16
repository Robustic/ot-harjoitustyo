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
import seacsp.logic.LogList;

public class DataFilesTest {
    private DataFiles dataFiles;
    private DataFile dataFile0;
    private DataFile dataFile1;
    
    @Before
    public void setUp() {
        LogList loglist = new LogList();
        this.dataFiles = new DataFiles(loglist);
        File file0 = new File(System.getProperty("user.dir") + "/" + "TestFile0.txt");
        File file1 = new File(System.getProperty("user.dir") + "/" + "TestFile1.txt");
        try {
            this.dataFile1 = this.dataFiles.addFile(file1);
            this.dataFile0 = this.dataFiles.addFile(file0);
        } catch (Exception e) {
            System.out.println("");
        }
        CheckBoxTreeItem objTrue = new CheckBoxTreeItem();
        objTrue.setSelected(true);
        CheckBoxTreeItem objFalse = new CheckBoxTreeItem();
        objFalse.setSelected(false);
        this.dataFile1.setReferenceToTreeItem(0, objTrue);
        this.dataFile1.setReferenceToTreeItem(1, objFalse);
        this.dataFile0.setReferenceToTreeItem(0, objFalse);
        this.dataFile0.setReferenceToTreeItem(1, objTrue);
        this.dataFile0.setReferenceToTreeItem(2, objFalse);
        this.dataFile0.setReferenceToTreeItem(3, objTrue);
        this.dataFile0.setReferenceToTreeItem(4, objTrue);        
    }
    
    @Test
    public void dataFilesExist() {        
        assertTrue(this.dataFiles != null);      
    }
    
    @Test
    public void fileAlreadyAdded() {
        File file1 = new File(System.getProperty("user.dir") + "/" + "TestFile1.txt");
        assertTrue(this.dataFiles.fileAlreadyAdded(file1));      
    }
    
    @Test
    public void fileAlreadyAddedToFiles() {
        File file1 = new File(System.getProperty("user.dir") + "/" + "TestFile1.txt");
        String message = "";
        try {
            this.dataFiles.addFile(file1);
        } catch (Exception e) {
            message = e.toString();
        }
        assertEquals("java.lang.IllegalArgumentException: File TestFile1.txt already added.", message);      
    }
    
    @Test
    public void fileNotYetAdded() {
        File filex = new File(System.getProperty("user.dir") + "/" + "TestFilex.txt");
        assertFalse(this.dataFiles.fileAlreadyAdded(filex));      
    }
        
    @Test
    public void recalculationNeeded01() {
        Phii phii = new Phii(-0.1);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        assertTrue(this.dataFiles.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded02() {
        Phii phii = new Phii(0.01);
        Frequencies frequencies = new Frequencies();
        assertTrue(this.dataFiles.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded00() {
        Phii phii = new Phii(-0.1);
        Frequencies frequencies = new Frequencies();
        assertFalse(this.dataFiles.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded1() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataFiles.neededToRecalculate(frequencies, phii);
        assertFalse(this.dataFiles.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded2() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataFiles.neededToRecalculate(frequencies, phii);
        phii = new Phii(0.050001);
        assertTrue(this.dataFiles.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded3() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataFiles.neededToRecalculate(frequencies, phii);
        frequencies.equalDivision(3.0001, 5.5, 1);
        assertTrue(this.dataFiles.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded4() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        this.dataFiles.neededToRecalculate(frequencies, phii);
        frequencies.equalDivision(3.1, 5.5, 1);        
        assertFalse(this.dataFiles.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void recalculationNeeded5() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(3.1, 5.5, 1);
        assertTrue(this.dataFiles.neededToRecalculate(frequencies, phii));
    }
    
    @Test
    public void calculationDone() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies = new Frequencies();
        frequencies.equalDivision(2.1, 6.5, 1);
        this.dataFiles.calculate(frequencies, phii);
        ArrayList<Spectrum> spectrumList = new ArrayList<>();
        this.dataFiles.getSpectrumList(spectrumList);
        assertEquals(4.29171705, spectrumList.get(0).getAccWithFrequency(0), 0.000001);
    }
    
    @Test
    public void noNeedForRecalculation() {
        Phii phii = new Phii(0.05);
        Frequencies frequencies1 = new Frequencies();
        frequencies1.equalDivision(2.1, 6.5, 1);
        this.dataFiles.calculate(frequencies1, phii);
        Frequencies frequencies2 = new Frequencies();
        frequencies2.equalDivision(3.1, 7.5, 1);
        this.dataFiles.copyFrequencies(frequencies2.getFrequenceList());
        this.dataFiles.calculate(frequencies2, phii);
        ArrayList<Spectrum> spectrumList = new ArrayList<>();
        this.dataFiles.getSpectrumList(spectrumList);        
        assertEquals(4.29171705, spectrumList.get(0).getAccWithFrequency(0), 0.000001);
    }
    
}
