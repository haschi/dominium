package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.dominium.jpa.JpaEreignisstrom
import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HaushaltsbuchWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt
import spock.lang.Ignore
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.EntityTransaction

class HibernateHaushaltsbuchRepositoryTest extends Specification {

    def "Repository benötigt einen JPA Entity Manager"() {
        when:
        new HibernateHaushaltsbuchRepository(null)

        then:
        thrown ArgumentIstNullException
    }

    def "Für neue Aggregate wird ein Ereignisstrom angelegt"() {

        given: "Angenommen ich habe ein Repository und ein Haushaltsbuch"
        EntityManager entityManager = EntityManagerProducer.entityManagerErzeugen();
        HaushaltsbuchEventStore eventStore = new HaushaltsbuchEventStore(entityManager)

        def hhb = new Haushaltsbuch(UUID.randomUUID())
        HibernateHaushaltsbuchRepository repository = new HibernateHaushaltsbuchRepository(eventStore);

        when: "Wenn ich das Haushaltsbuch dem Repository hinzufüge"
        def transaction = entityManager.getTransaction()
        transaction.begin()

        repository.add hhb

        transaction.commit()

        then: "Dann wird der JpaEreignisstrom gespeichert"

        def x = repository.findBy(hhb.identitätsmerkmal)
        x == hhb
    }

    def "Für neue Aggregate werden die aufgetretenen Ereignisse gespeichert"() {

        given: "Angenommen ich habe ein Repository und ein neues Haushaltsbuch"
        EntityManager entityManager = Mock(EntityManager)
        HaushaltsbuchEventStore eventStore = new HaushaltsbuchEventStore(entityManager)

        def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID())
        def repository = new HibernateHaushaltsbuchRepository(eventStore)

        when: "Wenn ich das Haushaltsbuch dem Repository hinzufüge"
        repository.add haushaltsbuch

        then:
        1 * entityManager.persist(new HaushaltsbuchWurdeAngelegt(haushaltsbuch.identitätsmerkmal))
    }

    def "Für bestehende aggregate werden die aufgetretenen Ereignisse gespeichert"() {
        given:
        EntityManager entityManager = Mock(EntityManager)
        HaushaltsbuchEventStore eventStore = new HaushaltsbuchEventStore(entityManager)

        def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID())
        def repository = new HibernateHaushaltsbuchRepository(eventStore);
        repository.add haushaltsbuch

        when: "Wenn ich die Änderungen des Haushaltsbuches speicher"
        haushaltsbuch.neuesKontoHinzufügen "Girokonto", Kontoart.Aktiv
        repository.save haushaltsbuch

        then:
        1 * entityManager.persist(new HaushaltsbuchWurdeAngelegt(haushaltsbuch.identitätsmerkmal))

        then:
        1 * entityManager.persist(new KontoWurdeAngelegt("Girokonto", Kontoart.Aktiv))
    }
}
