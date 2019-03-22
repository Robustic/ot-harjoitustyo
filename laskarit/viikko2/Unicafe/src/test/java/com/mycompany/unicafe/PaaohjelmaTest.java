package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PaaohjelmaTest {
    
    Paaohjelma paaohjelma = new Paaohjelma();
    
    @Before
    public void setUp() {
        paaohjelma = new Paaohjelma();
    }
    
    @Test
    public void luotuPaaohjelmaOlemassa() {
        assertTrue(paaohjelma!=null);        
    }
    
    @Test
    public void paaohjelmaKasitteleeKassapaatettaOikein() {
        Kassapaate kassapaate = new Kassapaate();      
        kassapaate.syoEdullisesti(240);
        kassapaate.syoMaukkaasti(450);
        assertEquals(100640, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void paaohjelmaKasitteleeMaksukorttiaOikein() {
        Kassapaate kassapaate = new Kassapaate();
        Maksukortti kortti = new Maksukortti(0);        
        kassapaate.lataaRahaaKortille(kortti, 500);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(260, kortti.saldo());
    }
    
    @Test
    public void toimiikoPaaohjelmanMainOikein() {        
        Kassapaate kassapaate = new Kassapaate();
        Maksukortti kortti = new Maksukortti(0);        
        kassapaate.lataaRahaaKortille(kortti, 500);
        kassapaate.syoEdullisesti(kortti);
        paaohjelma.main(new String[0]);
        assertEquals(260, kortti.saldo());
    }       
}
