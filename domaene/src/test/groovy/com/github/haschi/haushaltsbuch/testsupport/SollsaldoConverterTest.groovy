package com.github.haschi.haushaltsbuch.testsupport

import com.github.haschi.coding.aspekte.DarfNullSein
import com.github.haschi.haushaltsbuch.domaene.testsupport.SollsaldoConverter
import spock.lang.Specification
import spock.lang.Unroll

import javax.money.MonetaryAmount

public class SollsaldoConverterTest extends Specification {

    @Unroll
    def "Umwandlung der gültigen Eingabe #zeichenfolge in ein Sollsaldo"(
            final String zeichenfolge,
            final MonetaryAmount währungsbetrag) {

        given: "Angenommen ich habe einen SollsaldoConverter"
        def converter = new SollsaldoConverter();

        when: "Wenn ich einen gültigen Währungsbetrag transformiere"
        def result = converter.transform(zeichenfolge)

        then: "Dann erhalte ich ein Sollsaldo-Objekt mit Betrag und Währung"
        result.betrag == währungsbetrag

        where:
        [zeichenfolge, währungsbetrag] << BeispieleFürUmwandlungVonWährungsbeträgen.gültigeWährungsbeträge()
    }


    @Unroll
    def "Umwandlung der ungültigen Eingabe #zeichenfolge in ein Sollsaldo"(
            @DarfNullSein final String zeichenfolge,
            final Class ausnahme) {

        given: "Angenommen ich habe einen SollsaldoConverter"
        def converter = new SollsaldoConverter();

        when: "Wenn ich ein ungültige Eingabe transformiere"
        converter.transform(zeichenfolge)

        then: "Dann wird eine Ausnahme ausgelöst"
        thrown(ausnahme)

        where:
        [zeichenfolge, ausnahme] << BeispieleFürUmwandlungVonWährungsbeträgen.ungültigeWährungsbeträge()
    }
}

