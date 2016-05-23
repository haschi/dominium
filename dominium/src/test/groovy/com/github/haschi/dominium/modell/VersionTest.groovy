package com.github.haschi.dominium.modell

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class VersionTest extends Specification {

    def "Version nimmt #zahl als gültigen Wert entgegen"(final long zahl) {
        when: new Version(zahl)
        then: notThrown Exception

        where:
        zahl            || _
        0L              || _
        1L              || _
        1000L           || _
        Long.MAX_VALUE  || _
    }

    def "Version darf nicht negativ sein"(final long zahl) {
        when: new Version(zahl)
        then: thrown IllegalArgumentException

        where:
        zahl            || _
        -1L             || _
        -2L             || _
        -1000L          || _
        Long.MIN_VALUE  ||_
    }

    def "Version ist ein Wertobjekt"() {
        expect: EqualsVerifier.forClass Version verify()
    }

    def "Versionen haben eine natürliche Ordnung"() {
        given:
        Version version1 = new Version(zahl1)
        Version version2 = new Version(zahl2)

        when:
        int vergleich = version1.compareTo(version2)

        then:
        vergleich == erwartetesErgebnis

        where:
        zahl1   | zahl2 || erwartetesErgebnis
        1L      | 2L    || -1
        1L      | 1L    || 0
        2L      | 1L    || 1
    }

    def "Versionen haben einen Nachfolger"() {
        when:
        Version nachfolger = version.nachfolger();

        then:
        nachfolger > version
        nachfolger == version2

        where:
        version || version2
        new Version(0L) || new Version(1L)
        new Version(1000L) || new Version(1001L)
        new Version(Long.MAX_VALUE - 1) || new Version(Long.MAX_VALUE)
    }

    def "Version erkennt Überlauf"() {

        when:
        new Version(Long.MAX_VALUE).nachfolger()

        then:
        thrown IllegalStateException;
    }

    def "Es gibt eine Initialversion"() {
        expect:
        Version.NEU == new Version(0L)
    }
}
