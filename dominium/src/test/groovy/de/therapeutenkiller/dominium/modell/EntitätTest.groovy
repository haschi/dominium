package de.therapeutenkiller.dominium.modell

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import groovy.transform.CompileStatic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class EntitätTest extends Specification {

    def "Entitäten besitzen eine Identität"() {
        given:
        UUID id = UUID.randomUUID()

        when:
        Entität<UUID> entität = new Entität<UUID>(id);

        then:
        entität.getIdentitätsmerkmal() == id;
    }

    def "Äquivalenzregeln für Entitäten"() {
        expect: EqualsVerifier.forClass(Entität.class).verify()
    }

    def "Entitäten benötigen eine Identität"() {

        when: "die Entität ohne Identitätsmerkmal erzeugt wird"
        new Entität(null);

        then: "wird eine ArgumentIstNullException ausgelöst"
        thrown ArgumentIstNullException
    }

    class Person extends Entität<Long> {

        String vorname
        String nachname

        Person(long id) {
            super(id)
        }
    }

    def "Klassen können Entitäten sein"() {
        when:
        def entität = new Person(1L)

        then:
        entität != null
    }

    def "Für Abgeleitete Klassen gelten die Äquivalenzregeln"() {
        expect: EqualsVerifier.forClass(Person).verify()
    }
}
