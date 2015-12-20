package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.SollsaldoConverter
import spock.lang.Specification
import spock.lang.Unroll

public class SollsaldoConverterTest extends  Specification {

    @Unroll
    def "Umwandlung des gültigen Währungsbetrages #zeichenfolge in ein Sollsaldo"(String zeichenfolge, BigDecimal betrag, String währung) {

        given: "Angenommen ich habe einen MoneyConverter"
        def converter = new SollsaldoConverter();

        when: "Wenn ich einen gültigen Währungsbetrag transformiere"
        def result = converter.transform(zeichenfolge)

        then: "Dann erhalte ich ein Sollsaldo-Objekt mit Betrag und Währung"
        result.betrag.currency.currencyCode == währung
        result.betrag.number.toBigDecimal() == betrag

        where:
        [zeichenfolge, betrag, währung] << gültigeWährungsbeträge
        //zeichenfolge | betrag   | währung
        //"123,45 EUR" | 123.45   | "EUR"
        //"123.45 EUR" | 12345.00 | "EUR"
        //"-10,23 USD" | -10.23   | "USD"
        //"1  DEM"     | 1.00     | "DEM"
    }

    def getGültigeWährungsbeträge() {
        [
            [ "123,45 EUR", 123.45, "EUR" ],
            ["123.45 EUR", 12345.00, "EUR"],
            ["-10,23 USD", -10.23, "USD"],
            ["1  DEM", 1.00, "DEM"]
        ]
    }
}
