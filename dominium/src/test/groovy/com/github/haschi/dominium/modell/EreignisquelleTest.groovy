package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomäne.TestAggregatEreignisZiel
import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification

class EreignisquelleTest extends Specification {

    def "Ereignisse der Quelle hinzufügen erhöht die Version"() {

        given: "ich habe eine neue Ereignisquelle"
        UUID identitätsmerkmal = UUID.randomUUID()
        Ereignisquelle<TestAggregatEreignisZiel> ereignisquelle = new Ereignisquelle<>(Version.NEU.nachfolger());

        when: "ich der Quelle ein Ereignis auslöst"
        ereignisquelle.bewirkt(ZustandWurdeGeändert.of(42L))

        then: "wird die Versionsnummer des Aggregats in der Ereignisquelle erhöht"
        ereignisquelle.getVersion() == Version.NEU.nachfolger().nachfolger()
    }

    def "Eine neue Ereignisquelle besitzt die Anfangsversion"() {
        expect:
        new Ereignisquelle<TestAggregatEreignisZiel>().version == Version.NEU
    }
}