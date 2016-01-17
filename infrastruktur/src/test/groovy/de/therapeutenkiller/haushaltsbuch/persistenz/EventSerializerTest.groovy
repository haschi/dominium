package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne.ZustandWurdeGeändert
import spock.lang.Ignore
import spock.lang.Specification

class EventSerializerTest extends Specification {


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
