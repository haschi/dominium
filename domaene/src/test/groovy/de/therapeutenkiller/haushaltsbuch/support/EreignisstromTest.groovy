package de.therapeutenkiller.haushaltsbuch.support

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis
import de.therapeutenkiller.haushaltsbuch.domaene.support.Ereignisstrom
import de.therapeutenkiller.haushaltsbuch.domaene.support.EventWrapper
import spock.lang.Shared
import spock.lang.Specification

class EreignisstromTest extends Specification{

    @Shared String streamName = "Mein Ereignisstrom"

    def "Ereignisstrom kann Ereignisse registrieren"() {
        given: "Angenommen ich habe einen Ereignisstrom und ein Ereignis"

        Ereignisstrom ereignisstrom = new Ereignisstrom(streamName)
        Domänenereignis<String> ereignis = Mock()

        when: "Wenn ich ein Ereignis registriere"
        EventWrapper<String> wrapper = ereignisstrom.registerEvent(ereignis)

        then: "Dann wird ein EventWrapper erzeugt mit Version und streamName"
        wrapper.equals(new EventWrapper(ereignis, 1, streamName))
    }

    def "Ereignisstrom vergibt fortlaufende Versionsnummern"() {
        given: "Angenommen ich habe einen Ereignisstrom und ein Ereignis"
        Ereignisstrom ereignisstrom = new Ereignisstrom(streamName)
        Domänenereignis<String> ereignis = Mock()

        when: "Wenn ich mehrere Ereignisse registriere"
        def result = (1..5).collect { ereignisstrom.registerEvent(ereignis).version }

        then: "Dann werden die Versionsnummern fortlaufend aufsteigend sein."
        result == (1..5)

    }
}
