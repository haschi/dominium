package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.domaene.support.EventSerializer
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.TestEreignis
import spock.lang.Specification

class EventSerializerTest extends Specification {


    def "Serialisierung eines Ereignisses"() {
        given:
        TestEreignis ereignis = new TestEreignis("Matthias","Haschka");

        when:
        byte[] ergebnis = EventSerializer.serialize(ereignis);

        then:
        ereignis == (TestEreignis) EventSerializer.deserialize(ergebnis)
    }
}
