# SeismicAccelerationSpectrum -applikaation käyttöohje

Lataa tiedosto [SeismicAccelerationSpectrum-1.1.jar](https://github.com/Robustic/ot-harjoitustyo/releases/tag/viikko7)

## Sovelluksen tarkoitus

Sovelluksen avulla voidaan määrittää useammasta kiihtyvyyshistoriasta kiihtyvyysspektrien verhokäyrä. Kiihtyvyyshistoriat voidaan hakea laskemista varten yhdestä tai useammasta tekstitiedostosta. Kukin tiedosto voi sisältää yhden tai useamman kiihtyvyyshistorian. 

Kiihtyvyyshistoriat voidaan tallettaa SQL-tietokantaan ja tuoda ohjelmaan seuraavilla käyttökerroilla. Näin tekstitiedostosta lukua ei myöhemmin tavita, kun kiihtyvyysdata on talletettu hyvin organisoituun tietokantaa

## Sovelluksen käyttöohje

### Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar SeismicAccelerationSpectrum-1.1.jar
```

Ohjelman käynnistäminen avaa päänäkymän:

<img src="https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/kuvat/Paanakyma.png" width="1331">

### Tekstitiedoston avaaminen

Sovelluksessa voidaan avata sopivia tekstimuotoisia tiedostoja, joihin on talletettu kiihtyvyyden aikahistorioita.

Tiedosto avataan napilla _Select and Import Text-file_, jolloin avautuu valintaikkuna. Kun haluttu tekstimuotoinen (*.txt) tiedosto valitaan, käsittelee ohjelma tiedoston sisältämän datan ja näyttää tiedostosta löytyneet aikahistoriat hierarkiapuuna ohjelman vasemmassa reunassa.

### Vaimennuksen arvon valitseminen

Käytettävän vaimennuksen (Damping) lukuarvo voidaan valita Radio-button valinnalla, tai syöttämällä vapaavalintainen haluttu arvo tekstikenttään. Suurempi vaimennuksen arvo vaimentaa kiihtyvyyden aikahistorian aiheuttamia kiihtyvyksiä eri taajuuksilla.

### Taajuusvälin valitseminen

Taajuusväli voidaan valita Radio-button valinnalla. Käytössä on kaksi taajuusaluetta, joissa taajuus jakautuu tasaisin askelin.

### Verhokäyrän laskeminen

Vasemman reunan hierarkiapuusta valitaan halutut aikahistoriat. Painettaessa _Calculate and Draw Envelope Spectrum_ -nappia ohjelma laskee verhokäyrän käyttäen valittuja aikahistorioita. Ohjelma piirtää verhokäyrän oikeassa laidassa olevaan kuvaajaan.

Laskennan jälkeen pääkymään tulostuu spektrin kuvaaja:

<img src="https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/kuvat/CalculatedSpectrum.png" width="1329">

### Aikahistoroiden näyttäminen omassa ikkunassaan

Kun käyttäjä painaa _Draw Time Histories_ -nappia, ilmestyy uusi ikkuna, josta käyttäjä voi tarkastella valittuja aikahistorioita.

Aikahistoriat tulostuvat omaan ikkunaansa:

<img src="https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/kuvat/Timehistories.png" width="1206">

### Tietokannan luominen

Käyttäjä voi luoda uuden tietokantatiedoston painamalla nappia _Add New SQL-db_. Syöttämällä ilmestyvään ikkunaan uuden tietokannan tiedostonimi ilman tiedostopäätettä, oletushakemistoon ilmestyy uusi tietokantatiedosto annetulla nimellä tiedostopäätteen .db kanssa.

Uuden tietokannan nimi annetaan ilmestyvään ikkunaan:

<img src="https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/kuvat/AddDBFilename.png" width="306">

### Tietokannan avaaminen

Käyttäjä voi halutessaan avata olemassa olevan tietokantatiedoston painamalla nappia _Open and Import SQL-db_. Tällöin kaikki kyseisessä tietokannassa olevat aikahistoriat avautuvat ohjelmaan.

### Tallettaminen tietokantaan

Käyttäjä voi tallettaa kiihtyvyysaikahistoriat valitsemaansa tietokantatiedostoon painamalla nappia _Save Timehistories to SQL-db_. Tällöin kaikki ohjelmaan tekstitiedostoista tai tietokannoista avatut aikahistoriat tallettuvat tekstikentässä _Chosen SQL-database:_ valittuna olevaan tietokantatiedostoon, elleivät ne jo ole kyseisessä tietokantatiedostossa.

### Ohjelman sulkeminen

Ohjelma suljetaan painamlla nappia _Exit_. Tällöin ohjelma sulkeutuu ja kaikka tallentamattomat tiedot katoavat. Tietokantoihin talletettu tai tekstitiedostoissa oleva tieto säilyy ja niihin talletettu tieto voidaan avata ohjelmaan seuraavalla käyttökerralla.
