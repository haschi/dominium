package de.therapeutenkiller.dominium.persistenz.jpa

import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.ZustandWurdeGeändert
import spock.lang.Specification

class EventSerializerSpockTest extends Specification {

    def "Serialisierung eines Ereignisses"() {

        given:
        ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert(payload: 42L);

        when:
        byte[] ergebnis = EventSerializer.serialize(ereignis);

        then:
        ereignis == (ZustandWurdeGeändert) EventSerializer.deserialize(ergebnis)
    }
}
