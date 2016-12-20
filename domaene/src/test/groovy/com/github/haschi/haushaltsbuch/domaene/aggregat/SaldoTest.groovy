package com.github.haschi.haushaltsbuch.domaene.aggregat

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class SaldoTest extends Specification {

    def "Habensaldo implementieren equals und hashCode entsprechend der Spezifikation"() {
        expect:
        EqualsVerifier.forClass(Habensaldo).verify()
    }

    def "Sollsaldo implementieren equals und hashCode entsprechend der Spezifikation"() {
        expect:
        EqualsVerifier.forClass(Sollsaldo).verify()
    }

    def "Sollhabensaldo implementieren equals und hashCode entsprechend der Spezifikation"() {
        expect:
        EqualsVerifier.forClass(SollHabenSaldo).verify()
    }
}