package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomäne.TestAggregatEreignisZiel
import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification

class EreignisQuelleTest extends Specification {

    public static final long PAYLOAD = 42L

    def "Ereignisse der Quelle hinzufügen erhöht die Version"() {

        given: "ich habe eine neue Ereignis-Quelle"
        EreignisQuelle<TestAggregatEreignisZiel> ereignisQuelle = new EreignisQuelle<>(Version.NEU.nachfolger());

        when: "ich der Quelle ein Ereignis auslöst"
        ereignisQuelle.bewirkt(ZustandWurdeGeändert.of(PAYLOAD))

        then: "wird die Versionsnummer des Aggregats in der Ereignisquelle erhöht"
        ereignisQuelle.getVersion() == Version.NEU.nachfolger().nachfolger()
    }

    def "Eine neue Ereignis-Quelle besitzt die Anfangsversion"() {
        expect:
        new EreignisQuelle<TestAggregatEreignisZiel>().version == Version.NEU
    }

    def "Ereignisse werde an die Abonnenten weitergeleitet"() {
        given: "ich habe eine Ereignis-Quelle mit Abonnenten"
        EreignisQuelle<TestAggregatEreignisZiel> ereignisQuelle = new EreignisQuelle<>()
        def abonnent1 = Mock(TestAggregatEreignisZiel)
        def abonnent2 = Mock(TestAggregatEreignisZiel)

        ereignisQuelle.abonnieren(abonnent1)
        ereignisQuelle.abonnieren(abonnent2)

        when: "ich ein Ereignis auslöse"
        ereignisQuelle.bewirkt(ZustandWurdeGeändert.of(PAYLOAD))

        then:
        1 * abonnent1.falls(ZustandWurdeGeändert.of(PAYLOAD))
        1 * abonnent2.falls(ZustandWurdeGeändert.of(PAYLOAD))
    }

    def "Abonnenten können nur einmal registriert werden"() {
        given: "ich habe eine Ereignis-Quelle mit einem Abonnenten"
        EreignisQuelle<TestAggregatEreignisZiel> ereignisQuelle = new EreignisQuelle<>()
        TestAggregatEreignisZiel abonnent = Mock(TestAggregatEreignisZiel)
        ereignisQuelle.abonnieren(abonnent)

        when: "ich den Abonnenten ein zweites mal registriere und ein Ereignis auslöse"
        ereignisQuelle.abonnieren(abonnent)
        ereignisQuelle.bewirkt(ZustandWurdeGeändert.of(PAYLOAD))

        then: "wird der Abonnent das Ereignis nur einmal erhalten"
        1 * abonnent.falls(ZustandWurdeGeändert.of(PAYLOAD))
    }
}