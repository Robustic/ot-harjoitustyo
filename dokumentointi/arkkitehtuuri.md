# Arkkitehtuurikuvaus

## Käyttöliittymä

Käyttöliittymä sisältää viisi erillistä näkymää:
- päänäkymä
- kaksi tiedoston valintaikkunaa
- tietokannan nimenvalintaikkunan
- ikkunan, johon piirtyy valittuja aikahistorioita

## Tietojen pysyväistallennus

Pakkauksen _seacsp.db_ luokat _DataCollectionDao_ ja _TimehistoryDao_ huolehtivat tietojen tallettamisesta tietokantaan.

## Rakenne

Ohjelman rakenne noudattaa kerrosarkkitehtuuria. Rakenne on kuvattuna pakkauksina seuraavasti:

seacsp.ui
seacsp.logic
seacsp.data
seacsp.calculations

Ylimpänä on pakkaus seacsp.ui ja alimpana seacsp.calculations.

Ohjelmassa on lisäksi pakkaukset tiedostosta lukua (seascsp.file) ja tietokantaan talletusta varten (seacsp.db). Nämä pakkaukset sijoittuvat pakkauksen seacsp.data alle.

## Alustava rakenne luokkakaaviona

Ohjelman alustava rakenne luokkakaaviona:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Architecture.png" width="975">

Luokkakaaviossa ei ole mukana käyttöliittymää.

## Tiedoston lukeminen sekvenssikaaviona

Tiedoston lukeminen sekvenssikaaviona:

<img src="https://github.com/Robustic/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Sekvenssikaavio_tiedostonluku.png" width="947">
