package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomaene.TestAggregatEreignisZiel
import com.github.haschi.dominium.testdomaene.ZustandWurdeGeändert
import spock.lang.Specification

class EreignisQuelleTest extends Specification {

    public static final long PAYLOAD = 42L

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