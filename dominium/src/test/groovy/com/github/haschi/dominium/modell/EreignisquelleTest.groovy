package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomäne.TestAggregatEreignisZiel
import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification

class EreignisquelleTest extends Specification {

    public static final long PAYLOAD = 42L

    def "Ereignisse der Quelle hinzufügen erhöht die Version"() {

        given: "ich habe eine neue Ereignisquelle"
        Ereignisquelle<TestAggregatEreignisZiel> ereignisquelle = new Ereignisquelle<>(Version.NEU.nachfolger());

        when: "ich der Quelle ein Ereignis auslöst"
        ereignisquelle.bewirkt(ZustandWurdeGeändert.of(PAYLOAD))

        then: "wird die Versionsnummer des Aggregats in der Ereignisquelle erhöht"
        ereignisquelle.getVersion() == Version.NEU.nachfolger().nachfolger()
    }

    def "Eine neue Ereignisquelle besitzt die Anfangsversion"() {
        expect:
        new Ereignisquelle<TestAggregatEreignisZiel>().version == Version.NEU
    }

    def "Ereignisse werde an die Abonnenten weitergeleitet"() {
        given: "ich habe eine Ereignisquelle mit Abonnenten"
        Ereignisquelle<TestAggregatEreignisZiel> ereignisquelle = new Ereignisquelle<>()
        def abonnent1 = Mock(TestAggregatEreignisZiel)
        def abonnent2 = Mock(TestAggregatEreignisZiel)

        ereignisquelle.abonnieren(abonnent1)
        ereignisquelle.abonnieren(abonnent2)

        when: "ich ein Ereignis auslöse"
        ereignisquelle.bewirkt(ZustandWurdeGeändert.of(PAYLOAD))

        then:
        1 * abonnent1.falls(ZustandWurdeGeändert.of(PAYLOAD))
        1 * abonnent2.falls(ZustandWurdeGeändert.of(PAYLOAD))
    }

    def "Abonnenten können nur einmal registriert werden"() {
        given: "ich habe eine Ereignisquelle mit einem Abonnenten"
        Ereignisquelle<TestAggregatEreignisZiel> ereignisquelle = new Ereignisquelle<>()
        TestAggregatEreignisZiel abonnent = Mock(TestAggregatEreignisZiel)
        ereignisquelle.abonnieren(abonnent)

        when: "ich den Abonnenten ein zweites mal registriere und ein Ereignis auslöse"
        ereignisquelle.abonnieren(abonnent)
        ereignisquelle.bewirkt(ZustandWurdeGeändert.of(PAYLOAD))

        then: "wird der Abonnent das Ereignis nur einmal erhalten"
        1 * abonnent.falls(ZustandWurdeGeändert.of(PAYLOAD))
    }
}