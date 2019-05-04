# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla voidaan määrittää useammasta kiihtyvyyshistoriasta kiihtyvyysspektrien verhokäyrä. Kiihtyvyyshistoriat voidaan hakea laskemista varten yhdestä tai useammasta tekstitiedostosta. Kukin tiedosto voi sisältää yhden tai useamman kiihtyvyyshistorian. 

Kiihtyvyyshistoriat voidaan tallettaa SQL-tietokantaan ja tuoda ohjelmaan seuraavilla käyttökerroilla. Näin tekstitiedostosta lukua ei myöhemmin tarvita, kun kiihtyvyysdata on talletettu hyvin organisoituun tietokantaan.

## Teoriatausta

Laskennan tekninen tausta perustuu mekaniikan peruslaeista johdettuihin kaavoihin. Sovelluksessa lasketaan halutuille taajuuksille jousi-massa-vaimennin -värähtelijän avulla suurin kiihtyvyyden arvo käyttäen herätteenä annettua kiihtyvyyshistoriaa.

Aika-askeleelle *i* värähtelijän kiihtyvyys voidaan määrittää seuraavalla kaavalla:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/PictureAccelerationOfTheOscillator.png" width="895">

Kun laskettavat taajuudet valitaan riittävän tiheästi, voidaan tulosten perusteella määrittää kiihtyvyysspektri erilaisilla taajuuden arvoilla. Kun lähtötietoina käytetään useampaa kiihtyvyyshistoriaa, lasketaan kullekin taajuudelle tulosten maksimikiihtyvyys, jolloin saadaan tulokseksi verhokäyrä.

## Käyttöliittymä

Sovellus koostuu yhdestä päänäkymästä, joka selviää kuvasta:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Paanakyma.png" width="1331">

Yhteensä käyttöliittymä sisältää viisi erillistä näkymää:
- päänäkymä
- kaksi tiedoston valintaikkunaa
- uuden tietokantatiedoston nimensyöttönäkymä
- näkymä, johon piirtyy valitut aikahistoriakuvaajat

Ohjelmaa hallitaan sovellukseen liittyvillä napeilla ja tekemällä sopivat valinnat _Radio-button_ napeilla. Ohjelmaan voidaan avata sopivia tekstimuotoisia tiedostoja. Ohjelma tukee myös tiedon pitkäaikaistalletusta tietokantaan.

Tarkemmin käyttöliittymän toimintoja voi tarkastella [käyttöohjeesta](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/kayttoohje.md).

## Perusversion tarjoama toiminnallisuus

- käyttäjä voi avata yhden tai useamman tekstitiedoston laskentaa varten
  - avattavat tiedostot valitaan graafisella näkymällä
- avatut tiedostot ja niiden sisältämät kiihtyvyyshistoriat listataan puurakenteena, josta käyttäjä voi valita check-box valinnoilla kaaviokuvassa esitettävän verhokäyrän pohjana olevat kiihtyvyyshistoriat
- käyttäjä voi laskea ohjelmalla kiihtyvyyshistorioiden spektrit, lasketuista kiihtyvyysspektreistä muodostetaan verhokäyrä, joka näytetään käyttäjälle kaaviokuvana
  - käyttäjä voi määrittää taajuusalueen, jolla laskenta tehdään
  - käyttäjä voi määrittää laskennassa käytettävän vaimennuksen arvon
- käyttäjä voi tarkastella erillisnäkymässä kiihtyvyyshistorian kuvaajaa
- käyttäjä voi luoda uuden SQL-tietokantatiedoston
  - tietokannan nimi annetaan omassa näkymässään
- käyttäjä voi avata SQL-tietokantatiedoston ja tuoda siihen talletetut aikahistoriat ohjelmaan
  - avattava SQL-tietokantatiedosto valitaan graafisella näkymällä
  - tietokantaan talletetut aikahistoriat avautuvat ohjelmaan
- käyttäjä voi tallettaa ohjelman muistissa olevat kiihtyvyyshistoriat valittuun SQL-tietokantatiedostoon
- tiedostojenkäsittelyyn ja laskentaan liittyvistä asioista pidetään yllä loki-listaa, joka näytetään käyttäjälle vieritettävässä tulostusikkunassa

## Jatkokehitysideoita

Perusversiota voidaan täydentää esim. seuraavilla toiminnallisuuksilla:

- kiihtyvyysspektrin esittäminen logaritmisella taajuusasteikolla kaaviokuvassa
- kaikkien ohjelman käsittelemien tietojen talletusmahdollisuus
- mahdollisuus valita, mitä tietoja tietokannasta luetaan ohjelmaan
