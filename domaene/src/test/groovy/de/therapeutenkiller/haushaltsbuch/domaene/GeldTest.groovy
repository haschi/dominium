package de.therapeutenkiller.haushaltsbuch.domaene

import org.testng.annotations.Test
import spock.lang.Specification

/**
 * Created by mhaschka on 21.09.15.
 */
public class GeldTest extends Specification{

    def "wenn ich geld anzeige, dann wird die Währung angezeigt"() {
        given: def geld = new Geld(190, "€")
        expect: geld.toString().contains("€")
    }

    def "wenn ich geld anzeige, dann wird der Betrag angezeigt"() {
        given: def geld = new Geld(190, "€")
        expect: geld.toString().contains("190")
    }
}
