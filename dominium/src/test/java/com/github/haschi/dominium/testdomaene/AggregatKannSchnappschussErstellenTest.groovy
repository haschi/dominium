package com.github.haschi.dominium.testdomaene

import com.github.haschi.coding.aspekte.ArgumentIstNullException
import com.github.haschi.dominium.modell.Version
import com.github.haschi.dominium.testdomaene.ImmutableSnapshot
import com.github.haschi.dominium.testdomaene.TestAggregat
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatProxy
import spock.lang.Specification

class AggregatKannSchnappschussErstellenTest extends Specification {

    def "Ein Aggregat kann Schnappschüsse erstellen, wenn eine Schnappschussschnittstelle vorhanden ist"() {
        given:
        UUID identitätsmerkmal = UUID.randomUUID()
        TestAggregat aggregat = new TestAggregatProxy(identitätsmerkmal, Version.NEU.nachfolger(42));

        when:
        TestAggregat.Snapshot schnappschuss = TestAggregat.Snapshot.from(aggregat)

        then:
        schnappschuss == ImmutableSnapshot.builder().payload(0L).build()
    }

    def "Zum Erzeugen eines Schnappschusses muss ein Aggregat existieren"() {
        when: "Wenn ich den Schnappschuss aus einem nicht vorhandenen Aggregat erzeuge"
        TestAggregat.Snapshot.from(null)

        then: "werde ich ich eine ArgumentIstNullException Ausnahme erhalten"
        thrown ArgumentIstNullException
    }
}