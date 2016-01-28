package de.therapeutenkiller.dominium.modell

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class EntitätTest extends Specification {

    def "Entitäten besitzen eine Identität"() {
        given:
        def id = UUID.randomUUID()

        when:
        def entität = new Entität(id);

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

        String getVorname() {
            return vorname
        }

        void setVorname(String vorname) {
            this.vorname = vorname
        }
        String vorname

        String getNachname() {
            return nachname
        }

        void setNachname(String nachname) {
            this.nachname = nachname
        }
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
