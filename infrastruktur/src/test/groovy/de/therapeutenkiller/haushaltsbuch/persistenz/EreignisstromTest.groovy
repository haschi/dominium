package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.dominium.modell.Domänenereignis
import de.therapeutenkiller.dominium.persistenz.Umschlag
import de.therapeutenkiller.dominium.persistenz.jpa.JpaDomänenereignisUmschlag
import de.therapeutenkiller.dominium.persistenz.jpa.JpaEreignisMetaDaten
import de.therapeutenkiller.dominium.persistenz.jpa.JpaEreignisstrom
import spock.lang.Shared
import spock.lang.Specification

interface MeinEreignisTyp extends Domänenereignis<String>, Serializable {    }

class MeinEreignis implements MeinEreignisTyp {

    @Override
    void anwendenAuf(String aggregat) {

    }
}
class EreignisstromTest extends Specification{

    @Shared UUID streamName = UUID.randomUUID()

    def "Ereignisstrom kann Ereignisse registrieren"() {
        given: "Angenommen ich habe einen JpaEreignisstrom und ein Ereignis"

        JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(streamName)
        MeinEreignis ereignis = new MeinEreignis()

        when: "Wenn ich ein Ereignis registriere"
        Umschlag<Domänenereignis<String>, JpaEreignisMetaDaten> wrapper = ereignisstrom.registrieren(ereignis)

        then: "Dann wird ein JpaDomänenereignisUmschlag erzeugt mit Version und streamName"
        wrapper.metaDaten == new JpaEreignisMetaDaten(streamName, 1L)
    }

    def "Ereignisstrom vergibt fortlaufende Versionsnummern"() {
        given: "Angenommen ich habe einen JpaEreignisstrom und ein Ereignis"
        JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(streamName)
        Domänenereignis<String> ereignis = new MeinEreignis();

        when: "Wenn ich mehrere Ereignisse registriere"
        def result = (1..5).collect { ereignisstrom.registrieren(ereignis).metaDaten }

        then: "Dann werden die Versionsnummern fortlaufend aufsteigend sein."
        result == (1..5).collect { new JpaEreignisMetaDaten(streamName, it)}
    }
}
