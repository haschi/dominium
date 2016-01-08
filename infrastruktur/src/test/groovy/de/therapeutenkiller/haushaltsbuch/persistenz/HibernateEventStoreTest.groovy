package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.api.ereignis.HaushaltsbuchWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSnapshot
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis
import de.therapeutenkiller.haushaltsbuch.domaene.support.Ereignisstrom
import spock.lang.Shared
import spock.lang.Specification

import javax.persistence.EntityManager

class HibernateEventStoreTest extends Specification {

    @Shared streamName = "Test-Strom"
    Ereignisstrom strom = new Ereignisstrom(streamName)
    @Shared List<Domänenereignis<Haushaltsbuch>> ereignisse = [
            new HaushaltsbuchWurdeAngelegt(UUID.randomUUID()),
            new KontoWurdeAngelegt("Anfangsbestand", Kontoart.Aktiv)]


    def "Event-Stream beim Anlegen persistieren"() {

        EntityManager entityManager = Mock(EntityManager)

        given: "Angenommen ich habe einen Event-Store"
        def store = new HibernateEventStore<HaushaltsbuchSnapshot, Haushaltsbuch>(entityManager)
        Collection<Domänenereignis<Haushaltsbuch>> ereignisse = new ArrayList<Domänenereignis<Haushaltsbuch>>()

        when: "Wenn ich einen neues Event-Stream erzeuge"
        store.createNewStream(streamName, ereignisse)

        then: "Dann wird der Event-Stream persistiert"
        1 * entityManager.persist(new Ereignisstrom(streamName))
    }

    def "Ereignisse beim Anlegen persistieren"() {

        EntityManager entityManager = Mock(EntityManager) {
            find(Ereignisstrom.class, streamName) >> strom
        }

        given:
        def store = new HibernateEventStore<HaushaltsbuchSnapshot, Haushaltsbuch>(entityManager)

        when:
        store.createNewStream(streamName, ereignisse)

        then:
        1* entityManager.persist(strom)

        then:
        1 * entityManager.persist({it.ereignis.equals(ereignisse[0])})

        then:
        1 * entityManager.persist({it.ereignis.equals(ereignisse[1])})
    }

    def "Ereignisse einem vorhandenen Event-Stream hinzufügen"() {

        EntityManager entityManager = Mock(EntityManager) {
            find(Ereignisstrom, streamName) >> strom
        }

        given:
        def store = new HibernateEventStore(entityManager)

        when:
        store.appendEventsToStream(streamName, ereignisse, Optional.of(0))

        then:
        1 * entityManager.persist({it.ereignis.equals(ereignisse[0])})

        then:
        1 * entityManager.persist({it.ereignis.equals(ereignisse[1])})
    }
}
