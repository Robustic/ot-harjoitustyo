# SeismicAccelerationSpectrum

## Sovelluksen tarkoitus

Sovelluksen avulla voidaan määrittää useammasta kiihtyvyyshistoriasta kiihtyvyysspektrien verhokäyrä. Kiihtyvyyshistoriat voidaan hakea laskemista varten yhdestä tai useammasta tiedostosta. Kukin tiedosto voi sisältää yhden tai useamman kiihtyvyyshistorian.

## Dokumentaatio

[Käyttöohje](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/kayttoohje.md)

[Vaatimusmaarittely](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/Robustic/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/Robustic/ot-harjoitustyo/releases/tag/viikko6)


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

generoi hakemistoon _target_ suoritettavan jar-tiedoston _SeismicAccelerationSpectrum-1.02.jar_

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




