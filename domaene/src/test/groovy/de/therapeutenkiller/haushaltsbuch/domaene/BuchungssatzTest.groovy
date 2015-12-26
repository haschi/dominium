package de.therapeutenkiller.haushaltsbuch.domaene

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
        def habenkonto = new Konto("habenkonto")
        def sollkonto = new Konto("sollkonto")
        def währungsbetrag = 12.23.euro

        when: "Wenn ich daraus einen Nuchungssatz erzeuge"
        def buchungssatz = new Buchungssatz(sollkonto, habenkonto, währungsbetrag);

        then: "Dann sind Sollkonto, Habenkonto und Währungsbetrag Teil des Buchungssatzes"
        buchungssatz.getHabenkonto() == habenkonto;
        buchungssatz.getSollkonto() == sollkonto;
        buchungssatz.währungsbetrag == währungsbetrag;
    }

    def "Buchungssätze erfüllen die equals und hashCode Anforderungen"() {
        expect: EqualsVerifier.forClass(Buchungssatz.class)
                .usingGetClass()
                .verify();
    }

    def "Zeichenfolgenrepräsentation des Buchungssatzes"() {

        given:
        def MonetaryAmount währungsbetrag = 123.45.euro
        def Konto sollkonto = new Konto("Girokonto")
        def Konto habenkonto = new Konto("Lebensmittel")
        def buchungssatz = new Buchungssatz(sollkonto, habenkonto, währungsbetrag)

        expect: buchungssatz.toString() == "Girokonto (123,45 EUR) an Lebensmittel (123,45 EUR)"
    }

    def "Buchungssätze dürfen keine negativen Beträge besitzen"() {

        given:
        def MonetaryAmount währungsbetrag = 123.45.euro
        def Konto sollkonto = new Konto("Girokonto")
        def Konto habenkonto = new Konto("Lebensmittel")

        when: new Buchungssatz(sollkonto, habenkonto, währungsbetrag.negate())

        then: def exception = thrown(IllegalArgumentException)
        exception.message == "Buchungssätze dürfen keine negativen Beträge besitzen."
    }
}
