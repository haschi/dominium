package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto
import nl.jqno.equalsverifier.EqualsVerifier
import org.javamoney.moneta.Money
import spock.lang.Specification

import javax.money.Monetary

public final class BuchungssatzTest extends Specification {

    def "Wenn ich einen Buchungssatz habe"() {
        given:
        def euro = Monetary.getCurrency("EUR")
        def habenkonto = new Konto("habenkonto")
        def sollkonto = new Konto("sollkonto")
        def währungsbetrag = Money.of(12.23, euro)

        when: "Wenn ich einen Buchungssatz erzeugt habe"
        def buchungssatz = new Buchungssatz(sollkonto, habenkonto, währungsbetrag);

        then:
        buchungssatz.getHabenkonto() == habenkonto;
        buchungssatz.getSollkonto() == sollkonto;
        buchungssatz.währungsbetrag == währungsbetrag;
    }

    def "Buchungssätze erfüllen die equals und hashCode Anforderungen"() {
        expect: EqualsVerifier.forClass(Buchungssatz.class)
                .usingGetClass()
                .verify();
    }
}
