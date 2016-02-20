package de.therapeutenkiller.haushaltsbuch.persistenz
import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import spock.lang.Shared
import spock.lang.Specification

import javax.persistence.EntityManager

class HibernateHaushaltsbuchRepositoryTest extends Specification {

    @Shared TestUhr uhr = new TestUhr()

    def "Repository benötigt einen JPA Entity Manager"() {
        when:
        new HibernateHaushaltsbuchRepository(null)

        then:
        thrown ArgumentIstNullException
    }

    def "Für neue Aggregate wird ein Ereignisstrom angelegt"() {

        given: "Angenommen ich habe ein Magazin und ein Haushaltsbuch"
        EntityManager entityManager = EntityManagerProducer.entityManagerErzeugen();
        HaushaltsbuchEreignislager eventStore = new HaushaltsbuchEreignislager(entityManager, uhr)

        def hhb = new Haushaltsbuch(UUID.randomUUID())
        HibernateHaushaltsbuchRepository repository = new HibernateHaushaltsbuchRepository(eventStore);

        when: "Wenn ich das Haushaltsbuch dem Magazin hinzufüge"
        def transaction = entityManager.getTransaction()
        transaction.begin()

        repository.add hhb

        transaction.commit()

        then: "Dann wird der JpaEreignisstrom gespeichert"

        def x = repository.findBy(hhb.identitätsmerkmal)
        x == hhb
    }

    def "Für neue Aggregate werden die aufgetretenen Ereignisse gespeichert"() {

        given: "Angenommen ich habe ein Magazin und ein neues Haushaltsbuch"
        EntityManager entityManager = EntityManagerProducer.entityManagerErzeugen();
        HaushaltsbuchEreignislager eventStore = new HaushaltsbuchEreignislager(entityManager, uhr)

        def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID())
        def repository = new HibernateHaushaltsbuchRepository(eventStore)

        when: "Wenn ich das Haushaltsbuch dem Magazin hinzufüge"
        def transaction = entityManager.getTransaction()
        transaction.begin()

        repository.add haushaltsbuch

        transaction.commit()

        then:
        def x = repository.findBy(haushaltsbuch.identitätsmerkmal)
        x == haushaltsbuch
    }

    def "Für bestehende aggregate werden die aufgetretenen Ereignisse gespeichert"() {
        given:
        EntityManager entityManager = EntityManagerProducer.entityManagerErzeugen();
        HaushaltsbuchEreignislager eventStore = new HaushaltsbuchEreignislager(entityManager, uhr)

        def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID())
        def repository = new HibernateHaushaltsbuchRepository(eventStore);

        def transaction = entityManager.getTransaction()
        transaction.begin()

        repository.add haushaltsbuch

        transaction.commit()

        when: "Wenn ich die Änderungen des Haushaltsbuches speicher"
        haushaltsbuch.neuesKontoHinzufügen "Girokonto", Kontoart.Aktiv

        def transaction2 = entityManager.getTransaction()
        transaction2.begin()

        repository.save haushaltsbuch

        transaction2.commit()

        def ergebnis = repository.findBy(haushaltsbuch.identitätsmerkmal);

        then:

        ergebnis.konten.find {it.bezeichnung == "Girokonto"}
    }
}
