package de.therapeutenkiller.haushaltsbuch.support

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis
import de.therapeutenkiller.haushaltsbuch.domaene.support.Ereignisstrom
import de.therapeutenkiller.haushaltsbuch.domaene.support.JpaEreignisUmschlag
import spock.lang.Shared
import spock.lang.Specification

interface MeinEreignisTyp extends Domänenereignis<String>, Serializable {    }

class MeinEreignis implements MeinEreignisTyp {

    @Override
    void applyTo(String aggregat) {

    }
}
class EreignisstromTest extends Specification{

    @Shared String streamName = "Mein Ereignisstrom"



    def "Ereignisstrom kann Ereignisse registrieren"() {
        given: "Angenommen ich habe einen Ereignisstrom und ein Ereignis"

        Ereignisstrom ereignisstrom = new Ereignisstrom(streamName)
        MeinEreignis ereignis = new MeinEreignis()

        when: "Wenn ich ein Ereignis registriere"
        JpaEreignisUmschlag<String> wrapper = ereignisstrom.registerEvent(ereignis)

        then: "Dann wird ein JpaEreignisUmschlag erzeugt mit Version und streamName"
        wrapper.stream == streamName
        wrapper.version == 1
    }

    def "Ereignisstrom vergibt fortlaufende Versionsnummern"() {
        given: "Angenommen ich habe einen Ereignisstrom und ein Ereignis"
        Ereignisstrom ereignisstrom = new Ereignisstrom(streamName)
        Domänenereignis<String> ereignis = new MeinEreignis();

        when: "Wenn ich mehrere Ereignisse registriere"
        def result = (1..5).collect { ereignisstrom.registerEvent(ereignis).version }

        then: "Dann werden die Versionsnummern fortlaufend aufsteigend sein."
        result == (1..5)

    }
}
