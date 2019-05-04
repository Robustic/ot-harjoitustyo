# Testausdokumentti

Jotta ohjelman toiminnan mahdolliset virheet havaittaisiin mahdollisimman aikaisin kehitystyössä, on ohjelman testausta varten tehty JUnit testejä. JUnit testit on ajettu toistuvasti aina, kun ohjelmaan on lisätty uusia toiminnallisuuksia. Ajamalla testit aina uudelleen ja laajentamalla testien kattavuutta on varmistettu, että lisätty koodi ei aiheuta virhetoimintoja jo aiemmin testattuun koodiin. Ohjelmankehityksen aikana havaittiinkin muutamia virheitä, jotka saatiin näin korjattua ajoissa. 

## Yksikkö- ja integraatiotestaus

Yksikkö- ja integraatiotestauksella varmistettiin, että ohjelman osat toimivat oikein. Ohjelman osien toiminta varmistettiin niin sanotussa normaalitilanteessa, jolloin ohjelman saama data on oikeaa ja esimerkiksi tiedostojen luku tai tietokantatoiminnot toimivat oikein. Tämän lisäksi testattiin suuri joukko virhetilanteita, joilloin luettu tieto poikkeaa vaaditusta tai järjestelmä ei vastaa oletetulla tavalla. Näissä tapauksissa testeillä varmistettiin, että ohjelma toimii kaatumatta ja pystyy antamaan ongelmaan liittyvän selkeäkielisen virheviestin käyttäjälle.

### Pakkaus seacsp.calculations

Pakkaus [seacsp.calculations](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations) sisältää luokkien metodeissa numeerista laskemista, jossa pienikin virhe saattaa aiheuttaa suuria eroja tuloksissa. Tämän takia pakkauksen kaikkien luokkien metodien toiminta varmistettiin yksityiskohtaisilla testeillä [FrequenciesTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/calculations/FrequenciesTest.java), [PhiiTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/calculations/PhiiTest.java), [SpectrumTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/calculations/SpectrumTest.java) ja [TimehistoryTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/calculations/TimehistoryTest.java), joilla varmistetiin metodien antaman tuloksen oikeellisuus. Erityisesti luokan [Spectrum](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations/Spectrum.java) metodien antamat laskentatulokset testattiin yksityiskohtaisesti luokan SpectrumTest testeillä. Testeillä varmistettiin myös luokan [Spectrum](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations/Spectrum.java) toimivuus yhdessä luokkien [Frequencies](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations/Frequencies.java), [Phii](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations/Phii.java) ja [Timehistory](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations/Timehistory.java) kanssa. Koska esimerkiksi pakkauksen [seacsp.data](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/data) luokat käyttävät pakkauksen [seacsp.calculations](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations) luokkia, pakkauksen [seacsp.calculations](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations) luokkien toimintaa tuli testattua myös tätä kautta.

### Pakkaus seacsp.data

Pakkaus [seacsp.data](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/data) sisältää luokkia, joilla käsitellään hyvin suuria lukujoukkoja yhdellä kertaa. Käyttäjän saattaa olla hankalaa havaita virheitä, joissa osa tiedosta jää käsittelemättä tai käsittelyssä käsitellään osin väärää joukkoa. Tämän takia tämän pakkauksen luokkien toimintavarmuus pyrittiin varmistamaan testeillä, joissa käsiteltävää dataa muokattiin eri muotoihin ja järjestykseen siten, että mahdolliset puutteet ohjelman toiminnassa havaittaisiin. Tästä syystä testien lukumäärä testaavissa luokissa [DataCollectionTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/data/DataCollectionTest.java) ja [DataCollectionsTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/data/DataCollectionsTest.java) kasvoi suureksi.

### Pakkaus seacsp.file

Pakkaus [seacsp.file](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/file) sisältää tekstitiedostojen lukemiseen käytettäviä luokkia. Koska luokka [ReadFile](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/file/ReadFile.java) sisälsi tiedoston lukemisen merkkijonoksi 
[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), niin kyseiselle luokalle tehtiin erityisesti oikeiden tiedostojen lukemista merkkijonoksi testaavia testejä [ReadFileTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/file/ReadFileTest.java).

Tekstimuodossa olevan tiedoston data voi olla vaikealukuista, koska esimerkiksi välilyönneillä erotettujen lukujen välissä voi olla yksi tai mahdollisesti myös useampi välilyönti. Myös rivinvaihtojen määrä voi vaihdella. Riveillä saattaa olla myös eri määrä sarakkeita tai numeroiden joukossa muita merkkejä.

Tekstitiedostosta merkkijonoksi luetun tekstin muuttaminen käsiteltävään muotoon tehdään luokalla [ReadTextFile](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/file/ReadTextFile.java). Sitä testaavaan testausluokkaan [ReadTextFileTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/file/ReadTextFileTest.java) tuli hyvin suuri määrä testejä, joilla varmistettiin ohjelman kyky lukea tekstitiedostoja haluttuun muotoon ja erottaa milloin merkkijono ei ole luettavissa yksikäsitteisesti haluttuun muotoon. Näitäkin testejä tuli hyvin paljon.

### Pakkaus seacsp.db

Pakkaus [seacsp.db](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/db) sisältää luokkia, joilla käsitellään tietokantoja. Luokkien toiminnan oikeellisuus haluttiin testauksessa varmistaa tekemällä testauksen ajaksi automaattisesti ihan oikeat tietokantatiedostot, joita testauksessa käytettiin. Testausluokilla [DataCollectionDaoTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/db/DataCollectionDaoTest.java) ja [TimehistoryDaoTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/db/TimehistoryDaoTest.java) testattiin tietokantaan kirjoittamista ja sieltä lukemista.

### Pakkaus seacsp.logic

Pakkauksen [seacsp.logic](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/logic) luokat sisältävät käyttöliittymän kutsumia metodeja. Testausluokkien [LogicTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/logic/LogicTest.java) ja [LogListTest](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/test/java/seacsp/logic/LogListTest.java) testeillä varmistettiin ohjelman oikea toiminta kokonaisuutena siten, että sen käyttöliittymälle palauttamat arvot ovat järkeviä.

### Pakkaus seacsp.ui (Käyttöliittymä)

Käyttöliittymä jätettiin yksikkö- ja integraatiotestauksen ulkopuolelle.

### Testauskattavuus

Testauksen rivikattavuudeksi saatiin 97% ja haarautumakattavuudeksi 94%. Käyttöliittymän pakkaus seacsp.ui jätettiin testauksen ulkopuolelle, joten se ei vaikuta kattavuusprosentteihin.

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Jacoco.png" width="1004">

Ohjelmassa on pyritty varautumaan kattavasti mahdollisiin poikkeuksiin. Testauksessa ei löydetty keinoja generoida kaikkia poikkeustilanteita, joten niitä ei päästy testaamaan. Testauskattavuudessa ei siten päästy aivan sataan prosenttiin.
 
## Järjestelmätestaus

Koska ohjelman asentaminen ei vaadi toimenpiteitä, niin sen asentamista ei ollut tarvetta testata. 

Järjestelmätestaus suoritettiin Linux-järjestelmässä ajamalla ohjelma komentoriviltä, jolloin aukesi ohjelman graafinen näkymä. [Käyttöohjeen](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/kayttoohje.md) kuvaamia toiminnallisuuksia käytiin ohjelman kehityksen aikana läpi usein. Ohjelman valmistuttua testattiin erityisesti tietokannan käyttöä ja tietokantatiedoston tallettumista oikeaan paikkaan.

## Sovellukseen jääneet laatuongelmat

Kun sovellus käynnistetään komentoriviltä, tulee heti käynnistettäessä ilmoitus

```
Gtk-Message: 10:19:09.054: Failed to load module "appmenu-gtk-module"
```

Ohjelma toimii virheilmoituksesta huolimatta kuitenkin vakaasti, eikä mitään virheitä ilmoitukseen liittyen havaittu. Myöskään pajassa ei ilmoitusta nähty merkittävänä ongelmana.
