# SeismicAccelerationSpectrum

## Dokumentaatio

[Vaatimusmaarittely](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/Robustic/ot-harjoitustyo/tree/master/dokumentointi/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/Robustic/ot-harjoitustyo/releases/tag/viikko5)

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

generoi hakemistoon _target_ suoritettavan jar-tiedoston _OtmTodoApp-1.0-SNAPSHOT.jar_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/Robustic/ot-harjoitustyo/tree/master/SeismicAccelerationSpectrum/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_




