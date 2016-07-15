package com.github.haschi.haushaltsbuch.domaene.aggregat

import com.github.haschi.haushaltsbuch.api.Kontoname
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import javax.money.MonetaryAmount

public final class BuchungssatzTest extends Specification {

    def "Wenn ich einen Buchungssatz habe"() {
        given: "Angenommen ich habe ein Sollkonto, Habenkonto und Währungsbetrag"
        def währungsbetrag = 12.23.euro

        when: "Wenn ich daraus einen Buchungssatz erzeuge"
        def buchungssatz = new Buchungssatz(Kontoname.of("sollkonto"), Kontoname.of("habenkonto"), währungsbetrag);

        then: "Dann sind Sollkonto, Habenkonto und Währungsbetrag Teil des Buchungssatzes"
        buchungssatz.hatHabenkonto(Kontoname.of("habenkonto"))
        buchungssatz.hatSollkonto(Kontoname.of("sollkonto"))
        buchungssatz.währungsbetrag == währungsbetrag;
    }

    def "Buchungssätze erfüllen die equals und hashCode Anforderungen"() {
        expect: EqualsVerifier.forClass(Buchungssatz.class)
                .usingGetClass()
                .verify();
    }

    def "Zeichenfolgen-Repräsentation des Buchungssatzes"() {

        given:
        def MonetaryAmount währungsbetrag = 123.45.euro
        def buchungssatz = new Buchungssatz(
                Kontoname.of("Girokonto"),
                Kontoname.of("Lebensmittel"),
                währungsbetrag)

        expect: buchungssatz.toString() == "Girokonto (123,45 EUR) an Lebensmittel (123,45 EUR)"
    }

    def "Buchungssätze dürfen keine negativen Beträge besitzen"() {

        given:
        def MonetaryAmount währungsbetrag = 123.45.euro
        when: new Buchungssatz(
                Kontoname.of("Girokonto"),
                Kontoname.of("Lebensmittel"),
                währungsbetrag.negate())

        then: def exception = thrown(IllegalArgumentException)
        exception.message == "Buchungssätze dürfen keine negativen Beträge besitzen."
    }
}
