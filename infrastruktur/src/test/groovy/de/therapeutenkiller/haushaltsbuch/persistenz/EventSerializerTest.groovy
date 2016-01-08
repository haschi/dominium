package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.api.ereignis.HaushaltsbuchWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt
import spock.lang.Specification

class TestEreignis extends Wertobjekt implements Domänenereignis<UUID>, java.io.Serializable{

    String vorname;
    String nachname;

    TestEreignis(vorname, nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    @Override
    void applyTo(UUID aggregat) {

    }
}

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
