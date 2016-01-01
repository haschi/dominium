package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Habensaldo
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HabensaldoConverter
import groovy.transform.TypeChecked
import spock.lang.Specification
import spock.lang.Unroll

import javax.money.MonetaryAmount

class HabensaldoConverterTest extends Specification {

    @Unroll
    def "Gültige Eingabe #zeichenfolge transformieren"(
            final String zeichenfolge,
            final MonetaryAmount währungsbetrag) {

        given: "Angenommen ich habe einen HabensaldoConverter"
        def converter = new HabensaldoConverter()

        when: "Wenn ich die Zeichenfolge #zeichenfolge transformiere"
        Habensaldo result = converter.transform(zeichenfolge)

        then: "Dann erhalte ich ein Habensaldo-Objekt mit dem Währungsbetrag"
        result.betrag == währungsbetrag

        where:
        [zeichenfolge, währungsbetrag] << BeispieleFürUmwandlung.gültigeWährungsbeträge()
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
