package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.EreignisWurdeGeworfen
import spock.lang.Ignore
import spock.lang.Specification

class EventSerializerTest extends Specification {


    @Ignore
    def "Serialisierung eines Ereignisses"() {
        given:
        EreignisWurdeGeworfen ereignis = new EreignisWurdeGeworfen("Matthias","Haschka");

        when:
        byte[] ergebnis = EventSerializer.serialize(ereignis);

        then:
        ereignis == (EreignisWurdeGeworfen) EventSerializer.deserialize(ergebnis)
    }
}
