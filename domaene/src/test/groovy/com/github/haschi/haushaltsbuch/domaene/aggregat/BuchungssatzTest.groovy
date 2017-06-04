package com.github.haschi.haushaltsbuch.domaene.aggregat

import com.github.haschi.haushaltsbuch.api.Buchungssatz
import com.github.haschi.haushaltsbuch.api.Kontobezeichnung
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import javax.money.MonetaryAmount

final class BuchungssatzTest extends Specification {

    def "Wenn ich einen Buchungssatz habe"() {
        given: "Angenommen ich habe ein Sollkonto, Habenkonto und Geldbetrag"
        def geldbetrag = 12.23.euro

        when: "Wenn ich daraus einen Buchungssatz erzeuge"
        def buchungssatz = new Buchungssatz(Kontobezeichnung.of("sollkonto"), Kontobezeichnung.of("habenkonto"), geldbetrag)

        then: "Dann sind Sollkonto, Habenkonto und Geldbetrag Teil des Buchungssatzes"
        buchungssatz.hatHabenkonto(Kontobezeichnung.of("habenkonto"))
        buchungssatz.hatSollkonto(Kontobezeichnung.of("sollkonto"))
        buchungssatz.geldbetrag == geldbetrag
    }

    def "Buchungssätze erfüllen die equals und hashCode Anforderungen"() {
        expect:
        EqualsVerifier.forClass(Buchungssatz.class)
                .usingGetClass()
                .verify()
    }

    def "Zeichenfolgen-Repräsentation des Buchungssatzes"() {

        given:
        def geldbetrag = 123.45.euro
        def buchungssatz = new Buchungssatz(
                Kontobezeichnung.of("Girokonto"),
                Kontobezeichnung.of("Lebensmittel"),
                geldbetrag)

        expect:
        buchungssatz.toString() == "Girokonto (123,45 EUR) an Lebensmittel (123,45 EUR)"
    }

    def "Buchungssätze dürfen keine negativen Beträge besitzen"() {

        given:
        MonetaryAmount geldbetrag = 123.45.euro

        when:
        new Buchungssatz(
                Kontobezeichnung.of("Girokonto"),
                Kontobezeichnung.of("Lebensmittel"),
                geldbetrag.negate())

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Buchungssätze dürfen keine negativen Beträge besitzen."
    }
}
