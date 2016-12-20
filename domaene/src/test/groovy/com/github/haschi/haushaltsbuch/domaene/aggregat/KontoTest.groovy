package com.github.haschi.haushaltsbuch.domaene.aggregat

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class KontoTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier
                .forClass(Konto)
                .withOnlyTheseFields("bezeichnung")
                .verify()
    }
}
