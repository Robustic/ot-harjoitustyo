# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa kerrosarkkitehtuuria. Rakenne on kuvattuna pakkauksina seuraavasti:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/PackageStructure.png" width="821">

Ylimpänä on pakkaus _seacsp.ui_, joka sisältää JavaFX:llä toteutetun käyttöliittymän. Pakkaus _seacsp.logic_ sisältää sovelluslogiikan ja pakkaus _seacsp.data_ sisältää toiminnot tiedon hallintaan ja organisointiin. Pakkaus _seacsp.calculations_ vastaa laskentaan liittyvistä metodeista.

Ohjelmassa on lisäksi pakkaukset tekstitiedostosta lukua _seascsp.file_ ja tietokantaan talletusta ja tietokannasta lukemista varten _seacsp.db_. Nämä pakkaukset sijoittuvat pakkauksen seacsp.data alle, kuten myös pakkaus _seacsp.calculations_.

## Käyttöliittymä

Käyttöliittymä sisältää viisi erillistä näkymää:
- päänäkymä
- kaksi tiedoston valintaikkunaa
- uuden tietokantatiedoston nimensyöttönäkymä
- näkymä, johon piirtyy valitut aikahistoriakuvaajat

Päänäkymä, aikahistoriakuvaajien piirtonäkymä, sekä uuden tietokantatiedoston nimensyöttönäkymä on toteutettu kolmena [Stage](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html)-oliona. Jokainen niistä voi olla samaan aikaan auki. Tiedostojen valintaikkunat on toteutettu [FileChooser](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html)-oliona.

Päänäkymä muodostetaan seacsp.ui.SeismicAccelerationSpectrum luokassa, uuden tietokantatiedoston nimensyöttönäkymä seacsp.ui.NewDatabaseStage luokassa ja aikahistoriakuvaajien piirtonäkymä luokassa seacsp.ui.TimehistoriesStage.

Käyttöliittymä on eristetty sovelluslogiikasta niin hyvin, kuin mahdollista. Käyttöliittymän luokista tehdään kutsuja pääasiassa seacsp.logic.Logic luokan metodeihin, joilla ohjelma käsittelee tietoa halutulla tavalla.

Käyttöliittymän päänäkymän valintapuu [TreeView](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TreeView.html) sekä kuvaaja [LineChart](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/LineChart.html) pävittyvät käsitellyn tiedon mukaisiksi.

## Sovelluslogiikka

Pakauksen _seacsp.logic_ luokka seacsp.logic.Logic toimii pääasiallisena rajapintana käyttöliittymän suuntaan. Se hallinnoi käyttöliittymän kutsuja ja tarkistaa kutsujen oikeellisuuden, etteivät vääränlaiset kutsut pääsisi sotkemaan ohjelman tallettamaa tietoa. Luokan seacsp.logic.Logic olion sisältämä luokan seacsp.logic.LogList olio välittää käyttäjälle annettavat viestit käyttöliitymälle.

Luokka seacsp.logic.Logic sisältää myös luokan seacsp.data.DataCollections olion. Luokka seacsp.data.DataCollections toimii kokoajana useammalle luokan seacsp.data.DataCollection oliolle, mikä helpottaa tiedon hallinnoimista ja käsittelyä. Luokan seacsp.data.DataCollection olio toimii puolestaan kokoajana useammalle luokan seacsp.calculations.Timehistory oliolle, minkä metodeilla vaativin laskenta tapahtuu.

## Tietojen pysyväistallennus

Tekstitiedostosta lukua varten on pakkaus _seacsp.file_ ja sen luokat _seacsp.file.ReadFile_ ja _seacsp.file.ReadTextFile_.

Pakkauksen _seacsp.db_ luokat _seacsp.db.DataCollectionDao_ ja _seacsp.db.TimehistoryDao_ huolehtivat tietojen tallettamisesta tietokantaan. 

Tekstitiedoston luku voitasiin korvata suhteellisen helposti vaikka binääritiedostosta luvuksi, kuten myös tietokannasta lukeminen ja kirjoittaminen voitaisiin korvata binääritiedoston käsittelyksi. Tällöin korvattaisiin pakkausten _seacsp.file_ ja _seacsp.db_ luokat vastaavilla luokilla binääritiedostojen käsittelyyn.

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

Tekstitiedostossa on tietosolut erotettu toisistaan välilyönneillä ja rivinvaihdoilla. Tekstitiedoston lukemista on tehty virheitä kestäväksi, joten välilyöntien ja rivinvaihtojen määrä voi jonkin verran vaihdella ilman tiedostosta luvun häiriintymistä.

## Päätoiminnallisuudet

Ohjelma sisältää lukuisia toimintoja. Niiden kaikkien kuvaaminen ei ole mielekästä, mutta alla on kuvattu esimerkinomaisesti tiedostosta lukeminen sekvenssikaaviona.

### Tiedoston lukeminen sekvenssikaaviona

Tiedoston lukeminen sekvenssikaaviona:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Tiedostonluku.png" width="1188">

Saatuaan palautusarvona DataCollection luokan olion, käyttöliittymälogiikka päivittää päänäkymän valintapuun.

### Muut toiminnallisuudet

Muissa toiminnallisuukissa toimitaan vastaavalla tavalla. Käyttöliittymästä tulleen kutsun perusteella toimintalogiikka tekee tarvittavat kutsut tarvittaviin metodeihin, jotka palauttavat palautusarvon. Saadessa kontrollin takaisin, käyttöliittymä päivittää näkymät käyttäjälle ja jää odottamaan käyttäjän seuraavaa käskyä.

## Ohjelman rakenteeseen jääneet heikkoudet

### Käyttöliittymä

Käyttöliittymän koodi on tällä hetkellä aika sekava. Käyttöliittymä toimii kyllä halutulla tavalla, mutta ohjelmaa laajennettaessa selkeämpi koodi jaettuna loogisempiin kokonaisuuksiin helpottaisi koodin hallintaa.

### Paketteihin ja luokkiin jako

Paketteihin ja luokkiin jakoa voisi parantaa nykyisestä. Viimeistellympi jako voisi selkeyttää koodia ja vähentäisi kyselyitä suoraan luokkien läpi, mitkä tekevät nykyisen koodin paikoin vaikealukuiseksi.
