package de.therapeutenkiller.dominium.persistenz

import de.therapeutenkiller.dominium.modell.Version
import de.therapeutenkiller.dominium.modell.Versionsbereich
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification
import spock.lang.Unroll

class VersionsbereichTest extends Specification {

    @Unroll
    def "In einem Versionsbereich (#von, #bis) liegt #zahl innerhalb"() {

        when: "ich habe einen Versionsbereich von #von bis #bis"
        Versionsbereich versionsbereich = Versionsbereich.von new Version(von) bis bis

        then: "liegt #zahl innerhalb des Versionsbereichs"
        versionsbereich.liegtInnerhalb zahl

        where:
        von | bis || zahl
        1   | 10  || 1
        1   | 10  || 5
        1   | 10  || 10
        2   | 999 || 2
        2   | 999 || 500
        2   | 999 || 999
    }

    @Unroll
    def "In einem Versionsbereich (#von, #bis) liegt #zahl außerhalb"() {

        when:
        Versionsbereich versionsbereich = Versionsbereich.von new Version(von) bis bis

        then:
        !(versionsbereich.liegtInnerhalb(zahl))

        where:
        von | bis || zahl
        1   | 10  || 0
        1   | 10  || 11
        1   | 10  || 100
        2   | 999 || 1
        2   | 999 || 1000
    }

    @Unroll
    def "In einem Versionsbereich (#von, #bis) können keine ungültigen Versionen liegen"() {

        given: "Ich habe einen Versionsbereich #von #bis"
        Versionsbereich versionsbereich = Versionsbereich.von new Version(von) bis bis

        when: "ich prüfe, ob #zahl innerhalb des Versionsbereiches liegt"
        versionsbereich.liegtInnerhalb zahl

        then: "werde ich eine Ausnahme erhalten"
        thrown IllegalArgumentException

        where:
        von     | bis   || zahl
        1       | 10    || -1L
        10      | 100   || Long.MIN_VALUE
    }

    @Unroll
    def "Die Zahl #zahl liegt innerhalb des Versionsbereichs ALLE_VERSIONEN"(long zahl) {
        expect:
        Versionsbereich.ALLE_VERSIONEN.liegtInnerhalb(zahl)

        where:
        zahl           || _
        0              || _
        1              || _
        100            || _
        Long.MAX_VALUE || _
    }

    def "Die untere Grenze des Versionsbereichs darf nicht größer als die obere Grenze sein"() {

        when:
        Versionsbereich.von new Version(von) bis bis

        then:
        thrown exception

        where:
        von | bis || exception
        2   | 1   || IllegalArgumentException
        100 | 99  || IllegalArgumentException
    }

    def "Versionsbereiche mit gleichen Grenzen sind identisch"() {
        expect: EqualsVerifier.forClass(Versionsbereich).verify()
    }

    def "Die untere Grenze des Versionsbereichs muss eine positive Zahl sein"() {
        when:
        Versionsbereich.von new Version(von) bis 100

        then:
        thrown IllegalArgumentException

        where:
        von  || _
        -1   || _
        -100 || _
    }
}
