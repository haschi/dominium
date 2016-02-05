package de.therapeutenkiller.dominium.persistenz.jpa

import de.therapeutenkiller.dominium.memory.MemorySchnappschussMetaDaten
import de.therapeutenkiller.dominium.persistenz.Uhr
import spock.lang.Specification

import javax.inject.Inject
import javax.persistence.EntityManager

@Deltaspike
class EreignisstromPersistierenG extends Specification{

    @Inject
    EreignisstromPersistieren uhr;

    @Inject
    TestUhr uhr2

    @Inject
    EntityManager entityManager

    def "ich bin ein Test"() {
        expect:
        "Hurra" != null
    }

    def "ich bin noch ein Test"() {
        expect:
        uhr != null
    }

    def "dritter test"() {
        expect: uhr2 != null

    }

    def "vierter Test"() {
        expect: entityManager != null;
    }
}
