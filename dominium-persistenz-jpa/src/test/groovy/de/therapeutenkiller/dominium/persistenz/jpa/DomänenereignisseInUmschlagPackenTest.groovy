package de.therapeutenkiller.dominium.persistenz.jpa

import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregatEreignis
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.ZustandWurdeGeändert
import spock.lang.Specification

class DomänenereignisseInUmschlagPackenTest extends Specification {

    def "Serialisierung eines Ereignisses"() {

        given:
        ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert(42L);

        when:
        byte[] ergebnis = EventSerializer.serialize(ereignis);

        then:
        ereignis == (ZustandWurdeGeändert) EventSerializer.deserialize(ergebnis)
    }

    def "Domänenereignis Umschlag öffnen"() {

        given: "ich habe ein Domänenereignis-Umschlag mit einem Ereignis"
        ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert(42L)
        JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten("test-strom", 1L)
        JpaDomänenereignisUmschlag<TestAggregatEreignis> umschlag = new JpaDomänenereignisUmschlag<>(ereignis, meta)

        when: "ich den Umschlag öffne"
        def geöffnet = umschlag.öffnen()

        then: "werde ich das Domänenereignis erhalten"
        geöffnet == ereignis
    }
}
