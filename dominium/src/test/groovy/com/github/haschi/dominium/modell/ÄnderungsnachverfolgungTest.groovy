package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomäne.TestAggregatEreignisZiel
import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification

class ÄnderungsnachverfolgungTest extends Specification {

    def "Eine neue Änderungsnachverfolgung besitzt die Versionsnummer NEU"() {
        expect:
        new Änderungsnachverfolgung<TestAggregatEreignisZiel>().getVersion() == Version.NEU;
    }

    def "Änderungen führen zur Erhöhung der Version"() {

        given: "ich habe eine Änderungsnachverfolgung mit Version.NEU"
        Änderungsnachverfolgung<TestAggregatEreignisZiel> änderung = new Änderungsnachverfolgung<>()

        when: "ich eine Änderung registriere"
        änderung.falls(ZustandWurdeGeändert.of(42L))

        then:
        änderung.version == Version.NEU.nachfolger()
    }
}