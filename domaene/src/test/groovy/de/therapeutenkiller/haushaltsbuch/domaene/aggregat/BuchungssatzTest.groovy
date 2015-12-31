package de.therapeutenkiller.haushaltsbuch.domaene.aggregat

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto
import nl.jqno.equalsverifier.EqualsVerifier
import org.javamoney.moneta.Money
import spock.lang.Specification

import javax.money.Monetary
import javax.money.MonetaryAmount

public final class BuchungssatzTest extends Specification {

    def "Wenn ich einen Buchungssatz habe"() {
        given: "Angenommen ich habe ein Sollkonto, Habenkonto und Währungsbetrag"
        def währungsbetrag = 12.23.euro

        when: "Wenn ich daraus einen Buchungssatz erzeuge"
        def buchungssatz = new Buchungssatz("sollkonto", "habenkonto", währungsbetrag);

        then: "Dann sind Sollkonto, Habenkonto und Währungsbetrag Teil des Buchungssatzes"
        buchungssatz.getHabenkonto() == "habenkonto";
        buchungssatz.getSollkonto() == "sollkonto";
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
        def buchungssatz = new Buchungssatz("Girokonto", "Lebensmittel", währungsbetrag)

        expect: buchungssatz.toString() == "Girokonto (123,45 EUR) an Lebensmittel (123,45 EUR)"
    }

    def "Buchungssätze dürfen keine negativen Beträge besitzen"() {

        given:
        def MonetaryAmount währungsbetrag = 123.45.euro
        when: new Buchungssatz("Girokonto", "Lebensmittel", währungsbetrag.negate())

        then: def exception = thrown(IllegalArgumentException)
        exception.message == "Buchungssätze dürfen keine negativen Beträge besitzen."
    }
}
