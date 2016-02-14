package de.therapeutenkiller.dominium.persistenz.jpa

import de.therapeutenkiller.dominium.modell.Domänenereignis
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.NichtDeserialisierbar
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.NichtDeserialisierbarWegenFehlenderKlasse
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.NichtSerialisierbar
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.ZustandWurdeGeändert
import spock.lang.Specification

class DomänenereignisseInUmschlagPacken extends Specification {

    def "Serialisierung eines Ereignisses"() {

        given:
        ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert(payload: 42L);

        when:
        byte[] ergebnis = EventSerializer.serialize(ereignis);

        then:
        ereignis == (ZustandWurdeGeändert) EventSerializer.deserialize(ergebnis)
    }

    def "Domänenereignis Umschlag öffnen"() {

        given: "ich habe ein Domänenereignis-Umschlag mit einem Ereignis"
        ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert(payload: 42L)
        JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten("test-strom", 1L)
        JpaDomänenereignisUmschlag<TestAggregat> umschlag = new JpaDomänenereignisUmschlag<>(ereignis, meta)

        when: "ich den Umschlag öffne"
        def geöffnet = umschlag.öffnen()

        then: "werde ich das Domänenereignis erhalten"
        geöffnet == ereignis
    }

    def "Für nicht serialisierbare Ereignisse kann kein Umschlag erstellt werden"() {

        given: "ich habe ein nicht serialisierbares Ereignis"
        final Domänenereignis<TestAggregat> ereignis = new NichtSerialisierbar();
        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten("test-strom", 1L);

        when: "ich das Ereignis in einen Umschlag packe"
        def dummy = new JpaDomänenereignisUmschlag<>(ereignis, meta);

        then: "werde ich eine Ausnahme erhalten"
        thrown IllegalArgumentException
    }

    def "Nicht deserialisierbare Ereignisse können nicht aus dem Umschlag geholt werden"() {

        given:
        final Domänenereignis<TestAggregat> ereignis = new NichtDeserialisierbar();
        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten("test-strom", 1L);
        final JpaDomänenereignisUmschlag<TestAggregat> umschlag = new JpaDomänenereignisUmschlag<>(ereignis, meta);

        when:
        umschlag.öffnen();

        then:
        thrown Serialisierungsfehler
    }

    def "Ereignisse können nicht aus dem Umschlag geholt werden, wenn eine Klasse fehlt"() {

        given:
        final Domänenereignis<TestAggregat> ereignis = new NichtDeserialisierbarWegenFehlenderKlasse();
        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten("test-strom", 1L);
        final JpaDomänenereignisUmschlag<TestAggregat> umschlag = new JpaDomänenereignisUmschlag<>(ereignis, meta);

        when:
        umschlag.öffnen();

        then:
        thrown Serialisierungsfehler
    }
}
