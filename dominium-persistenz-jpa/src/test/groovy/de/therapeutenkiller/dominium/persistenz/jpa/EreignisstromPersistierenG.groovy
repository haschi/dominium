package de.therapeutenkiller.dominium.persistenz.jpa

import org.apache.deltaspike.jpa.api.transaction.Transactional
import spock.lang.Ignore
import spock.lang.Specification

import javax.inject.Inject
import javax.persistence.EntityManager

@Deltaspike
@Transactional
class EreignisstromPersistierenG extends Specification{

    @Inject
    TestUhr uhr2

    @Inject
    EntityManager entityManager

    def "ich bin ein Test"() {
        expect:
        "Hurra" != null
    }

    def "dritter test"() {
        expect: uhr2 != null

    }

    def "vierter Test"() {
        expect: entityManager != null;
    }

    @Ignore
    def "Ereignis-Ströme können persistiert werden"() {

        given:
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom("test-strom");
        ereignisstrom.setVersion(42L);

        when:
        this.entityManager.persist(ereignisstrom);
        this.entityManager.flush();
        this.entityManager.clear();

        final JpaEreignisstrom materialisiert = this.entityManager.find(JpaEreignisstrom.class, "test-strom");
        then:
        materialisiert == ereignisstrom
    }
}
