package com.github.haschi.haushaltsbuch.domaene.aggregat

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Shared
import spock.lang.Specification

import javax.money.MonetaryAmount

class SollsaldoTest extends Specification {

    @Shared
    MonetaryAmount betrag1 = 12.00.euro
    @Shared
    MonetaryAmount betrag2 = 12.00.euro

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier.forClass(Sollsaldo)
                .verify()
    }

    def "vergleich zweier Sollsalden"() {

        given:
        def saldo1 = new Sollsaldo(betrag1)
        def saldo2 = new Sollsaldo(betrag2)

        expect:
        saldo1.equals(saldo2)
    }

    def "kann nicht mit Habensaldo verglichen werden"() {
        given:
        def saldo1 = new Sollsaldo(betrag1)
        def saldo2 = new Habensaldo(betrag2)

        expect:
        saldo1.equals(saldo2)
    }

    def "kann mit Soll- Habensaldo verglichen werden"() {
        given:
        def betrag = 0.0.euro
        def saldo1 = new Sollsaldo(betrag)
        def saldo2 = new SollHabenSaldo()

        expect:
        saldo1.equals(saldo2)
        saldo2.equals(saldo1)
    }
}
