package de.therapeutenkiller.dominium.lagerung

import de.therapeutenkiller.dominium.aggregat.testdomäne.ZustandWurdeGeändert
import de.therapeutenkiller.dominium.jpa.EventSerializer
import spock.lang.Ignore
import spock.lang.Specification

class EventSerializerSpockTest extends Specification {

    @Ignore
    def "Serialisierung eines Ereignisses"() {

        given:
        ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert("Matthias","Haschka");

        when:
        byte[] ergebnis = EventSerializer.serialize(ereignis);

        then:
        ereignis == (ZustandWurdeGeändert) EventSerializer.deserialize(ergebnis)
    }
}
