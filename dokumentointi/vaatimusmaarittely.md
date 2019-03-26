# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla voidaan määrittää useammasta kiihtyvyyshistoriasta kiihtyvyysspektrien verhokäyrä. Kiihtyvyyshistoriat voidaan hakea laskemista varten yhdestä tai useammasta tiedostosta. Kukin tiedosto voi sisältää yhden tai useamman kiihtyvyyshistorian.

## Teoriatausta

Laskennan tekninen tausta perustuu mekaniikan peruslaeista johdettuihin kaavoihin. Sovelluksessa lasketaan halutuille taajuuksille jousi-massa-vaimennin -värähtelijän avulla suurin kiihtyvyyden arvo käyttäen herätteenä annettua kiihtyvyyshistoriaa.

Aika-askeleelle *i* värähtelijän kiihtyvyys voidaan määrittää seuraavalla kaavalla:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/PictureAccelerationOfTheOscillator.png" width="895">

Kun laskettavat taajuudet valitaan riittävän tiheästi, voidaan tulosten perusteella määrittää kiihtyvyysspektri erilaisilla taajuuden arvoilla. Kun lähtötietoina käytetään useampaa kiihtyvyyshistoriaa, lasketaan kullekin taajuudelle tulosten maksimikiihtyvyys, jolloin saadaan tulokseksi verhokäyrä.

## Käyttöliittymäluonnos

Sovellus koostuu vähintään yhdestä näkymästä, joka selviää seuraavasta kuvasta:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/PictureLayout1.png" width="949">

Ohjelmaa hallitaan sovellukseen liittyvillä napeilla.

## Perusversion tarjoama toiminnallisuus

- tiedostojenkäsittelyyn ja laskentaan liittyvistä asioista pidetään yllä loki-tiedostoa, josta kolme viimeisintä riviä näytetään käyttäjälle
- käyttäjä voi valita yhden tai useamman tiedoston yksitellen laskentaa varten
- käyttäjä voi poistaa listalta väärin valitun tiedoston
- käyttäjä voi määrittää taajuuspisteet, joilla laskenta tehdään
- käyttäjä voi määrittää laskennassa käytettävän vaimennuksen arvon
- kun käyttäjä painaa "laske"-nappia, lasketuista kiihtyvyysspektreistä muodostetaan verhokäyrä, joka näytetään käyttäjälle kaaviokuvana

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään aikataulun salliessa esim. seuraavilla toiminnallisuuksilla:

- kiihtyvyysspektrin esittäminen logaritmisella taajuusasteikolla kaaviokuvassa
- haluttujen tiedostojen valinta graafisella näkymällä
- käyttäjä voi tallettaa lasketut tiedot osin SQL-tietokantaan, osin tiedostoihin
- käyttäjä voi tarkastella tietokantaan ja tiedostoihin talletettuja tietoja
- valittujen tiedostojen ja niiden sisältämien kiihtyvyyshistorioiden listaus puurakenteena, josta käyttäjä voi valita check-box valinnoilla kaaviokuvassa esitettävän verhokäyrän pohjana olevat kiihtyvyyshistoriat
- käyttäjä voi tarkastella erillisnäkymässä värähtelijän kiihtyvyyshistoriaa ja verrata sitä laskennan pohjana olevaan herätteen kiihtyvyyshistoriaan
