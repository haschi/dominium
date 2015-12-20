package de.therapeutenkiller.haushaltsbuch.domaene.aggregat

import nl.jqno.equalsverifier.EqualsVerifier
import org.javamoney.moneta.Money
import spock.lang.Shared
import spock.lang.Specification

import javax.money.Monetary
import javax.money.MonetaryAmount

/**
 * Created by matthias on 20.12.15.
 */
class SollsaldoTest extends Specification {

    @Shared MonetaryAmount betrag1 = Money.of(12, Monetary.getCurrency(Locale.GERMANY));
    @Shared MonetaryAmount betrag2 = Money.of(12, Monetary.getCurrency(Locale.GERMANY));

    def "erfüllt die equals und hashCode Soezifikation"() {
        expect: EqualsVerifier.forClass(Sollsaldo)
        .withRedefinedSuperclass()
        .verify()
    }

    def "vergleich zweier Sollsalden"() {

        given:
        def saldo1 = new Sollsaldo(betrag1);
        def saldo2 = new Sollsaldo(betrag2);

        expect: saldo1.equals(saldo2)
    }

    def "kann nicht mit Habensaldo verglichen werden"() {
        given:
        def saldo1 = new Sollsaldo(betrag1);
        def saldo2 = new Habensaldo(betrag2);

        expect: !saldo1.equals(saldo2)
    }
}
