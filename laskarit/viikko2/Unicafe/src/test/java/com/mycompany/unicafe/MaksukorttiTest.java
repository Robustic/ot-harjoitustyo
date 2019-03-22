package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());      
    }
    
    @Test
    public void lataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(8345);
        assertEquals("saldo: 93.45", kortti.toString());      
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaTarpeeksi() {
        kortti.otaRahaa(255);
        kortti.otaRahaa(400);
        kortti.otaRahaa(125);
        assertEquals("saldo: 2.20", kortti.toString());      
    }
    
    @Test
    public void saldoEiMuutuJosRahaaLiianVahan() {
        kortti.otaRahaa(955);
        kortti.otaRahaa(55);
        assertEquals("saldo: 0.45", kortti.toString());
    }
    
    @Test
    public void palautaTrueJosRahatRiittavat() {
        assertTrue(kortti.otaRahaa(835));
    }
    
    @Test
    public void palautaFalseJosRahatEivatRiita() {
        assertFalse(kortti.otaRahaa(1005));
    }
    
    @Test
    public void palautaSaldoOikein() {
        kortti.otaRahaa(235);
        assertEquals(765, kortti.saldo());
    }
}
