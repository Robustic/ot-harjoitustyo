# SeismicAccelerationSpectrum

## Sovelluksen tarkoitus

Sovelluksen avulla voidaan määrittää useammasta kiihtyvyyshistoriasta kiihtyvyysspektrien verhokäyrä. Kiihtyvyyshistoriat voidaan hakea laskemista varten yhdestä tai useammasta tekstitiedostosta. Kukin tiedosto voi sisältää yhden tai useamman kiihtyvyyshistorian. 

Kiihtyvyyshistoriat voidaan tallettaa SQL-tietokantaan ja tuoda ohjelmaan seuraavilla käyttökerroilla. Näin tekstitiedostosta lukua ei myöhemmin tavita, kun kiihtyvyysdata on talletettu hyvin organisoituun tietokantaan.

## Dokumentaatio

[Käyttöohje](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/Robustic/ot-harjoitustyo/releases/tag/viikko5_1.01) (release 1.01)

[Viikko 6](https://github.com/Robustic/ot-harjoitustyo/releases/tag/viikko6) (release 1.02)

[Loppupalautus](https://github.com/Robustic/ot-harjoitustyo/releases/tag/loppupalautus) (release 1.1)


## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _SeismicAccelerationSpectrum-1.1.jar_

Jar ohjelma suoritetaan komennolla

```
java -jar SeismicAccelerationSpectrum-1.1.jar
```

siinä kansiossa, missä jar-paketti sijaitsee.

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
