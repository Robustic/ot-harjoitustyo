package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate kassapaate;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
    }
    
    @Test
    public void luotuKassapaateOlemassa() {
        assertTrue(kassapaate!=null);      
    }
    
    @Test
    public void kassapaatteenSaldoAlussaOikein() {
        assertEquals(100000, kassapaate.kassassaRahaa());      
    }
    
    @Test
    public void syoEdullisestiLisaaKassaanOikeinJosRahaaRiittavasti() {
        kassapaate.syoEdullisesti(2275);
        assertEquals(100240, kassapaate.kassassaRahaa());      
    }
    
    @Test
    public void syoEdullisestiPalauttaaOikeinJosRahaaRiittavasti() {
        assertEquals(2035, kassapaate.syoEdullisesti(2275));      
    }
    
    @Test
    public void syoEdullisestiEiLisaaKassaaJosRahaaLiianVahan() {
        kassapaate.syoEdullisesti(235);
        assertEquals(100000, kassapaate.kassassaRahaa());      
    }
    
    @Test
    public void syoEdullisestiPalauttaaOikeinJosRahaaLiianVahan() {
        assertEquals(235, kassapaate.syoEdullisesti(235));      
    }
    
    @Test
    public void syoMaukkaastiLisaaKassaanOikeinJosRahaaRiittavasti() {
        kassapaate.syoMaukkaasti(2275);
        assertEquals(100400, kassapaate.kassassaRahaa());      
    }
    
    @Test
    public void syoMaukkaastiPalauttaaOikeinJosRahaaRiittavasti() {
        assertEquals(1875, kassapaate.syoMaukkaasti(2275));      
    }
    
    @Test
    public void syoMaukkaastiEiLisaaKassaaJosRahaaLiianVahan() {
        kassapaate.syoMaukkaasti(395);
        assertEquals(100000, kassapaate.kassassaRahaa());      
    }
    
    @Test
    public void syoMaukkaastiPalauttaaOikeinJosRahaaLiianVahan() {
        assertEquals(395, kassapaate.syoMaukkaasti(395));      
    }
    
    @Test
    public void edullistenMaaraAlussaOikein() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());      
    }
    
    @Test
    public void edullistenMaaraKasvaaOikein() {
        kassapaate.syoEdullisesti(new Maksukortti(500));
        kassapaate.syoEdullisesti(240);        
        kassapaate.syoEdullisesti(235);
        kassapaate.syoEdullisesti(10000);
        assertEquals(3, kassapaate.edullisiaLounaitaMyyty());      
    }
    
    @Test
    public void maukkaittenMaaraAlussaOikein() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());      
    }
    
    @Test
    public void maukkaittenMaaraKasvaaOikein() {
        kassapaate.syoMaukkaasti(400);
        kassapaate.syoMaukkaasti(new Maksukortti(500));
        kassapaate.syoMaukkaasti(300);
        assertEquals(2, kassapaate.maukkaitaLounaitaMyyty());      
    }
    
    @Test
    public void kortinSaldoKasvaaOikein() {
        Maksukortti kortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(kortti, 123);
        assertEquals(123, kortti.saldo());      
    }
    
    @Test
    public void kassanSaldoKasvaaOikein() {
        Maksukortti kortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(kortti, 123);
        assertEquals(100123, kassapaate.kassassaRahaa());      
    }
    
    @Test
    public void kortinSaldoEiKasvaJosNegatiivinenMaksu() {
        Maksukortti kortti = new Maksukortti(110);
        kassapaate.lataaRahaaKortille(kortti, -125);
        assertEquals(110, kortti.saldo());      
    }
    
    @Test
    public void kassanSaldoEiKasvaJosNegatiivinenMaksu() {
        Maksukortti kortti = new Maksukortti(110);
        kassapaate.lataaRahaaKortille(kortti, -125);
        assertEquals(100000, kassapaate.kassassaRahaa());      
    }
    
    @Test
    public void syoEdullisestiVahentaaKortiltaOikeinJosSaldoaRiittavasti() {
        Maksukortti kortti = new Maksukortti(250);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(10, kortti.saldo());      
    }
    
    @Test
    public void syoEdullisestiPalauttaaTrueJosSaldoaRiittavasti() {
        Maksukortti kortti = new Maksukortti(250);
        assertTrue(kassapaate.syoEdullisesti(kortti));      
    }
    
    @Test
    public void syoEdullisestiEiVahennaKortiltaJosSaldoEiRiita() {
        Maksukortti kortti = new Maksukortti(235);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(235, kortti.saldo());     
    }
    
    @Test
    public void syoEdullisestiPalauttaaFalseJosRahaEiRiita() {
        Maksukortti kortti = new Maksukortti(235);
        assertFalse(kassapaate.syoEdullisesti(kortti));      
    }
    
    @Test
    public void syoEdullisestiLisaaEdullistenMaaraaJosSaldoaRiittavasti() {
        Maksukortti kortti = new Maksukortti(240);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());      
    }
    
    @Test
    public void syoEdullisestiEiLisaaEdullistenMaaraaJosSaldoEiRiita() {
        Maksukortti kortti = new Maksukortti(235);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());      
    }
    
    @Test
    public void syoMaukkaastiVahentaaKortiltaOikeinJosSaldoaRiittavasti() {
        Maksukortti kortti = new Maksukortti(405);
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(5, kortti.saldo());      
    }
    
    @Test
    public void syoMaukkaastiPalauttaaTrueJosSaldoaRiittavasti() {
        Maksukortti kortti = new Maksukortti(400);
        assertTrue(kassapaate.syoMaukkaasti(kortti));      
    }
    
    @Test
    public void syoMaukkaastiEiVahennaKortiltaJosSaldoEiRiita() {
        Maksukortti kortti = new Maksukortti(395);
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(395, kortti.saldo());     
    }
    
    @Test
    public void syoMaukkaastiPalauttaaFalseJosRahaEiRiita() {
        Maksukortti kortti = new Maksukortti(399);
        assertFalse(kassapaate.syoMaukkaasti(kortti));      
    }
    
    @Test
    public void syoMaukkaastiLisaaMaukkaittenMaaraaJosSaldoaRiittavasti() {
        Maksukortti kortti = new Maksukortti(400);
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());      
    }
    
    @Test
    public void syoMaukkaastiEiLisaaMaukkaittenMaaraaJosSaldoEiRiita() {
        Maksukortti kortti = new Maksukortti(395);
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());      
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuJosOstetaanKortilla() {
        Maksukortti kortti = new Maksukortti(9000);
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(100000, kassapaate.kassassaRahaa());      
    }
}
