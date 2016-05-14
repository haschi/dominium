package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.coding.aspekte.DarfNullSein
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter
import spock.lang.Specification
import spock.lang.Unroll

import javax.money.MonetaryAmount

class MoneyConverterTest  extends Specification {

    @Unroll
    def "Umwandlung gültiger Eingabe #zeichenfolge in einen Währungsbetrag"(
            final String zeichenfolge,
            final MonetaryAmount währungsbetrag) {

        given: "Angenommen ich habe einen MoneyConverter"
        def converter = new MoneyConverter();

        when: "Wenn ich einen gültigen Währungsbetrag transformiere"
        def result = converter.transform(zeichenfolge)

        then: "Dann erhalte ich ein Money-Objekt mit Betrag und Währung"
        result == währungsbetrag

        where:
        [zeichenfolge, währungsbetrag] << BeispieleFürUmwandlungVonWährungsbeträgen.gültigeWährungsbeträge()
    }

    @Unroll
    def "Umwandlung von #zeichenfolge führt zu #ausnahme"(
            @DarfNullSein final String zeichenfolge,
            final Class ausnahme) {

        given: "Angenommen ich habe einen MoneyConverter"
        def converter = new MoneyConverter();

        when: "Wenn ich einen ungültigen Währungsbetrag konvertiere"
        converter.transform(zeichenfolge)

        then: "Denn erhalte ich eine Ausnahme"
        Exception e = thrown()
        e.getClass() == ausnahme

        where:
        [zeichenfolge, ausnahme] << BeispieleFürUmwandlungVonWährungsbeträgen.ungültigeWährungsbeträge()
    }
}
