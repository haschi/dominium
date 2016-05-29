package com.github.haschi.dominium.modell

import com.github.haschi.coding.aspekte.ArgumentIstNullException
import com.github.haschi.dominium.testdomaene.TestAggregat
import spock.lang.Specification

class AggregatKannSchnappschussErstellenTest extends Specification {

    def "Ein Aggregat kann Schnappschüsse erstellen, wenn eine Schnappschussschnittstelle vorhanden ist"() {
        given:
        UUID identitätsmerkmal = UUID.randomUUID()
        TestAggregat aggregat = new TestAggregat(identitätsmerkmal, Version.NEU.nachfolger(42));

        when:
        TestAggregat.TestAggregatSchnapp schnappschuss = TestAggregat.TestAggregatSchnapp.from(aggregat)

        then:
        schnappschuss.version() == Version.NEU.nachfolger(42);
    }

    def "Zum Erzeugen eines Schnappschusses muss ein Aggregat existieren"() {
        when: "Wenn ich den Schnappschuss aus einem nicht vorhandenen Aggregat erzeuge"
        TestAggregat.TestAggregatSchnapp.from(null)

        then: "werde ich ich eine ArgumentIstNullException Ausnahme erhalten"
        thrown ArgumentIstNullException
    }
}