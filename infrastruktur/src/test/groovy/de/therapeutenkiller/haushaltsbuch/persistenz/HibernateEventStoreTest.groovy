package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HaushaltsbuchWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss
import de.therapeutenkiller.dominium.aggregat.Domänenereignis
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject
import javax.persistence.EntityManager

@Ignore
class HibernateEventStoreTest extends Specification {

    // @Inject
    EntityManager entityManager;

    @Shared streamName = "Test-Strom"
    JpaEreignisstrom strom = new JpaEreignisstrom(streamName)
    @Shared List<Domänenereignis<Haushaltsbuch>> ereignisse = [
            new HaushaltsbuchWurdeAngelegt(UUID.randomUUID()),
            new KontoWurdeAngelegt("Anfangsbestand", Kontoart.Aktiv)]


    def "Event-Stream beim Anlegen persistieren"() {

        given: "Angenommen ich habe einen Event-Store"
        def store = new HibernateEventStore<HaushaltsbuchSchnappschuss, Haushaltsbuch>()
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
        def store = new HibernateEventStore<HaushaltsbuchSchnappschuss, Haushaltsbuch>()

        when:
        store.neuenEreignisstromErzeugen(streamName, ereignisse)

        then:
        1* entityManager.persist(strom)

        then:
        1 * entityManager.persist({it.version == 1}) // TODO: Weitere Attribute prüfen

        then:
        1 * entityManager.persist({it.version == 2})
    }

    def "Ereignisse einem vorhandenen Event-Stream hinzufügen"() {

        EntityManager entityManager = Mock(EntityManager) {
            find(JpaEreignisstrom, streamName) >> strom
        }

        given:
        def store = new HibernateEventStore()

        when:
        store.ereignisseDemStromHinzufügen(streamName, ereignisse, Optional.of(0L))

        then:
        1 * entityManager.persist({it.version == 1})

        then:
        1 * entityManager.persist({it.version == 2})
    }
}
