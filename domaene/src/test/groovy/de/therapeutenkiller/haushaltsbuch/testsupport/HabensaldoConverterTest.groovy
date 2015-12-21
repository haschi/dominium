package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HabensaldoConverter
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by matthias on 21.12.15.
 */
class HabensaldoConverterTest extends Specification {

    @Unroll
    def "Gültige Eingabe #zeichenfolge transformieren"(final String zeichenfolge, final BigDecimal betrag, final String währung) {

        given: "Angenommen ich habe einen HabensaldoConverter"
        def converter = new HabensaldoConverter()

        when: "Wenn ich die Zeichenfolge #zeichenfolge transformiere"
        def result = converter.transform(zeichenfolge)

        then: "Dann erhalte ich ein Habensaldo-Objekt mit Betrag #betrag und Währung #währung"
        result.betrag.currency.currencyCode == währung
        result.betrag.number.toBigDecimal() == betrag

        where:
        [zeichenfolge, betrag, währung] << BeispieleFürUmwandlung.gültigeWährungsbeträge()
    }

    @Unroll
    def "Ungültige Eingabe #zeichenfolge transformieren"(final String zeichenfolge, final Class ausnahme) {

        given: "Angenommen ich habe einen HabensaldoConverter"
        def converter = new HabensaldoConverter();

        when: "Ich die Zeichenfolge #zeichenfolge transformiere"
        converter.transform(zeichenfolge)

        then: "Dann wird eine Ausnahme ausgelöst"
        thrown ausnahme

        where:
        [zeichenfolge, ausnahme] << BeispieleFürUmwandlung.ungültigeWährungsbeträge()
    }
}
