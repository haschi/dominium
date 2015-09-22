package de.therapeutenkiller.haushaltsbuch.domaene

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

/**
 * Created by mhaschka on 21.09.15.
 */
@Title("Beschreibe Geld")
@Subject(Geld)
public class GeldTest extends Specification {

    def "wenn ich geld anzeige, dann wird die Währung angezeigt"() {
        given:
        def geld = new Geld(190, "€")
        expect:
        geld.toString().contains("€")
    }

    def "wenn ich geld anzeige, dann wird der Betrag angezeigt"() {
        given:
        def geld = new Geld(190, "€")
        expect:
        geld.toString().contains("190")
    }

    def "Geld erfüllt die Äquivalenzkriterien"() {
        expect:
        EqualsVerifier.forClass(Geld.class).verify()
    }
}
