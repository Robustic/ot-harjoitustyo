# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa kerrosarkkitehtuuria. Rakenne on kuvattuna pakkauksina seuraavasti:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/PackageStructure.png" width="821">

Ylimpänä on pakkaus [seacsp.ui](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/ui), joka sisältää JavaFX:llä toteutetun käyttöliittymän. Pakkaus [seacsp.logic](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/logic) sisältää sovelluslogiikan ja pakkaus [seacsp.data](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/data) sisältää toiminnot tiedon hallintaan ja organisointiin. Pakkaus [seacsp.calculations](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations) vastaa laskentaan liittyvistä metodeista.

Ohjelmassa on lisäksi pakkaukset tekstitiedostosta lukua [seacsp.file](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/file) ja tietokantaan talletusta ja tietokannasta lukemista varten [seacsp.db](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/db). Nämä pakkaukset sijoittuvat pakkauksen [seacsp.data](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/data) alle, kuten myös pakkaus [seacsp.calculations](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations).

## Käyttöliittymä

Käyttöliittymä sisältää viisi erillistä näkymää:
- päänäkymä
- kaksi tiedoston valintaikkunaa
- uuden tietokantatiedoston nimensyöttönäkymä
- näkymä, johon piirtyy valitut aikahistoriakuvaajat

Päänäkymä, aikahistoriakuvaajien piirtonäkymä, sekä uuden tietokantatiedoston nimensyöttönäkymä on toteutettu kolmena [Stage](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html)-oliona. Jokainen niistä voi olla samaan aikaan auki. Tiedostojen valintaikkunat on toteutettu [FileChooser](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html)-oliona.

Päänäkymä muodostetaan [seacsp.ui.SeismicAccelerationSpectrum](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/ui/SeismicAccelerationSpectrum.java) luokassa, uuden tietokantatiedoston nimensyöttönäkymä [seacsp.ui.NewDatabaseStage](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/ui/NewDatabaseStage.java) luokassa ja aikahistoriakuvaajien piirtonäkymä luokassa [seacsp.ui.TimehistoriesStage](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/ui/TimehistoriesStage.java).

Käyttöliittymä on eristetty sovelluslogiikasta niin hyvin, kuin mahdollista. Käyttöliittymän luokista tehdään kutsuja pääasiassa [seacsp.logic.Logic](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/logic/Logic.java) luokan metodeihin, joilla ohjelma käsittelee tietoa halutulla tavalla.

Käyttöliittymän päänäkymän valintapuu [TreeView](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TreeView.html) sekä kuvaaja [LineChart](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/LineChart.html) pävittyvät käsitellyn tiedon mukaisiksi.

## Sovelluslogiikka

Pakauksen [seacsp.logic](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/logic) luokka [seacsp.logic.Logic](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/logic/Logic.java) toimii pääasiallisena rajapintana käyttöliittymän suuntaan. Se hallinnoi käyttöliittymän kutsuja ja tarkistaa kutsujen oikeellisuuden, etteivät vääränlaiset kutsut pääsisi sotkemaan ohjelman tallettamaa tietoa. Luokan [seacsp.logic.Logic](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/logic/Logic.java) olion sisältämä luokan [seacsp.logic.LogList](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/logic/LogList.java) olio välittää käyttäjälle annettavat viestit käyttöliitymälle.

Luokka [seacsp.logic.Logic](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/logic/Logic.java) sisältää myös luokan [seacsp.data.DataCollections](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/data/DataCollections.java) olion. Luokka [seacsp.data.DataCollections](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/data/DataCollections.java) toimii kokoajana useammalle luokan [seacsp.data.DataCollection](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/data/DataCollection.java) oliolle, mikä helpottaa tiedon hallinnoimista ja käsittelyä. Luokan [seacsp.data.DataCollection](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/data/DataCollection.java) olio toimii puolestaan kokoajana useammalle luokan [seacsp.calculations.Timehistory](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/calculations/Timehistory.java) oliolle, minkä metodeilla vaativin laskenta tapahtuu.

## Tietojen pysyväistallennus

Tekstitiedostosta lukua varten on pakkaus [seacsp.file](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/file) ja sen luokat [seacsp.file.ReadFile](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/file/ReadFile.java) ja [seacsp.file.ReadTextFile](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/file/ReadTextFile.java).

Pakkauksen [seacsp.db](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/db) luokat [seacsp.db.DataCollectionDao](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/db/DataCollectionDao.java) ja [seacsp.db.TimehistoryDao](https://github.com/Robustic/ot-harjoitustyo/blob/master/SeismicAccelerationSpectrum/src/main/java/seacsp/db/TimehistoryDao.java) huolehtivat tietojen tallettamisesta tietokantaan. 

Tekstitiedoston luku voitasiin korvata suhteellisen helposti vaikka binääritiedostosta luvuksi, kuten myös tietokannasta lukeminen ja kirjoittaminen voitaisiin korvata binääritiedoston käsittelyksi. Tällöin korvattaisiin pakkausten [seacsp.file](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/file) ja [seacsp.db](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/src/main/java/seacsp/db) luokat vastaavilla luokilla binääritiedostojen käsittelyyn.

### Luettavat tekstitiedostot

Sovellus lukee tekstitiedostoa seuraavassa formaatissa:

<pre>
t       No1          No2         No3        No4          No5
0       0            0           0          0            0
0.002   0.00086124   0.000904    0.0001162  -0.00079635  -0.00094346
0.004   0.00175956   0.0017971   0.0001824  -0.00166552  -0.00185123
0.006   0.00269121   0.0026789   0.00019844 -0.00259763  -0.0027162
...
</pre>

Tekstitiedostossa on tietosolut erotettu toisistaan välilyönneillä ja rivinvaihdoilla. Tekstitiedoston lukemista on tehty virheitä kestäväksi, joten välilyöntien ja rivinvaihtojen määrä voi jonkin verran vaihdella ilman tiedostosta lukemisen häiriintymistä.

### Tietokantaan talletus

Ohjelma käyttää SQLite tietokantaa. SQLite tietokannan käyttöä varten käytetään ulkoisena kirjastona JDBC-kirjastoa.

Käyttäjä voi luoda uuden tyhjän tietokannan, jolloin tietokannassa on tyhjät taulut Datacollection ja Timehistory. Käyttäjä voi myös avata tietokannan ja tallettaa tietokantaan.

#### Datacollection -taulu

_Datacollection_ -taulu muodostuu _id_-sarakkeesta ja _Datacollection_:in nimestä

```
id                  INTEGER PRIMARY KEY AUTOINCREMENT
name                VARCHAR(255)
```

#### Timehistory -taulu

_Timehistory_ -taulussa on käytössä sarakkeet

```
id                  INTEGER PRIMARY KEY AUTOINCREMENT
name                VARCHAR(255)
deltat              DOUBLE
datacollection_id   INTEGER)
```

Sarake datacollection_id viittaa _Datacollection_ -taulun vastaavaan objektiin. Näin _Timehistory_ -taulun riveistä on mahdollisuus viitata _Datacollection_ -taulun objekteihin. Tätä hyödynnetään tietokantahauissa.

#### Timehistorylist<NNN> -taulu

_Timehistory_ -oliot sisältävät listan _timehistory_, jossa voi olla kymmeniä tuhansia alkioita. Kun tietokantaan voidaan puolestaan tallettaa tuhansia aikahistorioita, on tehokkuuden kannalta järkevää käsitellä tietokannassa suuria _timehistory_ -listoja omina tauluinaan. Tätä varten jokaista _Timehistory_ -taulun jokaista riviä kohden generoidaan rivin luontivaiheessa taulu nimeltään, Timehistorylist<NNN>, missä <NNN> paikalla on _Timehistory_ taulun objektin _id_. Näin tieto pysyy tallessa hyvin järjestyneenä ja tietokannan käyttö pysyy tehokkaana.

Jokaisessa _Timehistorylist<NNN>_ -taulussa on sarakkeet

```
id                  INTEGER PRIMARY KEY AUTOINCREMENT
acceleration        DOUBLE
```

## Päätoiminnallisuudet

Ohjelma sisältää lukuisia toimintoja. Niiden kaikkien kuvaaminen ei ole mielekästä, mutta alla on kuvattu esimerkinomaisesti tekstitiedostosta lukeminen sekä tietokantaan talletus sekvenssikaaviona.

### Tiedoston lukeminen sekvenssikaaviona

Tiedoston lukeminen sekvenssikaaviona:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Tiedostonluku.png" width="1200">

Saatuaan palautusarvona DataCollection luokan olion, käyttöliittymälogiikka päivittää päänäkymän valintapuun.

### Tietokantaan talletus sekvenssikaaviona

Tietokantaan talletus sekvenssikaaviona:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/TietokantaanTalletus.png" width="1195">

### Muut toiminnallisuudet

Muissa toiminnallisuukissa toimitaan vastaavalla tavalla. Käyttöliittymästä tulleen kutsun perusteella toimintalogiikka tekee tarvittavat kutsut tarvittaviin metodeihin, jotka palauttavat palautusarvon. Saadessaan kontrollin takaisin, käyttöliittymä päivittää näkymät käyttäjälle ja jää odottamaan käyttäjän seuraavaa käskyä.

## Ohjelman rakenteeseen jääneet heikkoudet

### Käyttöliittymä

Käyttöliittymän koodi on tällä hetkellä aika sekava. Käyttöliittymä toimii kyllä halutulla tavalla, mutta ohjelmaa laajennettaessa selkeämpi koodi jaettuna loogisempiin kokonaisuuksiin helpottaisi koodin hallintaa.

### Paketteihin ja luokkiin jako

Paketteihin ja luokkiin jakoa voisi parantaa nykyisestä. Viimeistellympi jako voisi selkeyttää koodia ja vähentäisi kyselyitä suoraan luokkien läpi, mitkä tekevät nykyisen koodin paikoin vaikealukuiseksi.
