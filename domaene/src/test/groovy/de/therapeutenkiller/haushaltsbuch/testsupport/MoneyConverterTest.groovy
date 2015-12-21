package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter
import spock.lang.Specification
import spock.lang.Unroll

import javax.money.format.MonetaryParseException

class MoneyConverterTest  extends Specification {

    @Unroll
    def "Umwandlung gültiger Währungsbeträge"(
            final String zeichenfolge,
            final BigDecimal betrag,
            final String währung) {
        given: "Angenommen ich habe einen MoneyConverter"
        def converter = new MoneyConverter();

        when: "Wenn ich einen gültigen Währungsbetrag transformiere"
        def result = converter.transform(zeichenfolge)

        then: "Dann erhalte ich ein Money-Objekt mit Betrag und Währung"
        result.currency.currencyCode == währung;
        result.number.toBigDecimal() == betrag

        where:
        [zeichenfolge, betrag, währung] << BeispieleFürUmwandlung.gültigeWährungsbeträge()
    }

    @Unroll
    def "Umwandlung ungültiger Währungsbeträge"(final String zeichenfolge, final Class ausnahme) {

        given: "Angenommen ich habe einen MoneyConverter"
        def converter = new MoneyConverter();

        when: "Wenn ich einen ungültigen Währungsbetrag konvertiere"
        converter.transform(zeichenfolge)

        then: "Denn erhalte ich eine Ausnahme"
        Exception e = thrown()
        e.getClass() == ausnahme

        where:
        [zeichenfolge, ausnahme] << BeispieleFürUmwandlung.ungültigeWährungsbeträge()
    }
}
