package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.dominium.modell.Domänenereignis
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom
import de.therapeutenkiller.dominium.persistenz.Umschlag
import de.therapeutenkiller.dominium.persistenz.jpa.JpaEreignislager
import de.therapeutenkiller.dominium.persistenz.jpa.JpaEreignisstrom
import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss

import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import javax.persistence.EntityManager

class HibernateEventStoreTest extends Specification {

    @Shared streamName = "Test-Strom"
    JpaEreignisstrom strom = new JpaEreignisstrom(streamName)
    @Shared List<Domänenereignis<Haushaltsbuch>> ereignisse = [
            new KontoWurdeAngelegt("Anfangsbestand", Kontoart.Aktiv),
            new KontoWurdeAngelegt("Girokonto", Kontoart.Aktiv),
            new BuchungWurdeAusgeführt("Anfangsbestand", "Girokonto", 10.00.euro)]

    @Shared TestUhr uhr = new TestUhr();

    def "Event-Stream beim Anlegen persistieren"() {

        given: "Angenommen ich habe einen Event-Store"
        EntityManager entityManager = Mock(EntityManager)

        def store = new JpaEreignislager<Haushaltsbuch, UUID>(entityManager, uhr)
        Collection<Domänenereignis<Haushaltsbuch>> ereignisse = new ArrayList<Domänenereignis<Haushaltsbuch>>()

        when: "Wenn ich einen neues Event-Stream erzeuge"
        store.neuenEreignisstromErzeugen(streamName, ereignisse)

        then: "Dann wird der Event-Stream persistiert"
        1 * entityManager.persist(new JpaEreignisstrom(streamName))
    }

    def "Ereignisse beim Anlegen persistieren"() {

        EntityManager entityManager = Mock(EntityManager) {
            find(JpaEreignisstrom.class, streamName) >> strom
        }

        given:
        def store = new JpaEreignislager<Haushaltsbuch, UUID>(entityManager, uhr)

        when:
        store.neuenEreignisstromErzeugen(streamName, ereignisse)

        then:
        1 * entityManager.persist({it.version == 1 && it instanceof Umschlag}) // TODO: Weitere Attribute prüfen


        1 * entityManager.persist({it.version == 2 && it instanceof Umschlag})


        1* entityManager.persist {it instanceof Ereignisstrom} //(strom)
    }

    def "Ereignisse einem vorhandenen Event-Stream hinzufügen"() {

        EntityManager entityManager = Mock(EntityManager) {
            find(JpaEreignisstrom, streamName) >> strom
        }

        given:
        def store = new JpaEreignislager<Haushaltsbuch, UUID>(entityManager, uhr)

        when:
        store.ereignisseDemStromHinzufügen(streamName, 0L, ereignisse)

        then:
        1 * entityManager.persist({it.version == 1})

        then:
        1 * entityManager.persist({it.version == 2})
    }
}
