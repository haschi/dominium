package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne.TestAggregat
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne.TestAggregatSchnappschuss
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification


class AggregatwurzelTest extends Specification {


    def "Eine Aggregatwurzel ist eine Entität mit Identitätsmerkmal"() {

        given: "ich habe ein Identitätsmerkmal"
        UUID identitätsmerkmal = UUID.randomUUID()

        when: "ich ein Aggregat mit Identitätsmerkmal erzeuge"
        TestAggregat aggregat = new TestAggregat(identitätsmerkmal)

        then: "werde ich ein Aggregat mit Identitätsmerkmal erhalten"
        aggregat.identitätsmerkmal == identitätsmerkmal;
    }

    def "Eine Aggregatwurzel merkt sich ein aufgetretenes Ereignisse"() {

        given: "ich habe eine Aggregatwurzel"
        TestAggregat aggregat = new TestAggregat(UUID.randomUUID())

        when: "ich eine Änderung am Aggregat durchführe"
        aggregat.zustandÄndern 42L

        then: "wird die Zustandsänderung gemerkt"
        aggregat.änderungen.contains(new ZustandWurdeGeändert(42L))
    }

    def "Eine Aggregatwurzel merkt sich Ereignisse in der Reihenfolge, in der sie auftreten"() {

        given: "ich habe eine Aggregatwurzel"
        TestAggregat aggregat = new TestAggregat(UUID.randomUUID())

        when: "ich mehrere Änderungen am Aggregat durchführe"
        aggregat.zustandÄndern 42L
        aggregat.zustandÄndern 43L

        then:
        aggregat.änderungen == [new ZustandWurdeGeändert(42L), new ZustandWurdeGeändert(43L)]
    }

    def "Eine Aggregatwurzel führt Änderungen am Zustand sofort durch"() {

        given: "ich habe eine Aggregatwurzel"
        TestAggregat aggregat = new TestAggregat(UUID.randomUUID())

        when: "ich eine Zustandsänderung durchführe"
        aggregat.zustandÄndern(42L)

        then: "wird das Aggregat den neuen Zustand besitzen"
        aggregat.zustand == 42L
    }

    def "Ein neues Aggregat besitzt die Version 0"() {
        expect:
        new TestAggregat(UUID.randomUUID()).version == 0
    }

    def "Ein Aggregat erhält die Version seines Schnappschusses"() {

        given: "ich habe ein Schnappschuss des Aggregats"
        TestAggregatSchnappschuss schnappschuss = new TestAggregatSchnappschuss()
        schnappschuss.with {
            version = 42L
            payload = 4711
            identitätsmerkmal = UUID.randomUUID()
        }

        when: "ich das Aggregat aus dem Schnappschuss wiederherstelle"
        TestAggregat aggregat = new TestAggregat(schnappschuss)

        then: "wird das Aggregat die Version des Schnappschusses besitzen"
        aggregat.version == schnappschuss.version
    }

    def "Ein Aggregat erhält seine Identität aus dem Schnappschuss"() {

        given: "ich habe den Schnappschuss eine Aggregats"
        TestAggregatSchnappschuss schnappschuss = new TestAggregatSchnappschuss();
        schnappschuss.identitätsmerkmal = UUID.randomUUID()

        when: "ich das Aggregat aus dem Schnappschuss wiederherstelle"
        TestAggregat aggregat = new TestAggregat(schnappschuss)

        then: "wird das Aggregat die Identität aus dem Schnappschuss erhalten"
        aggregat.identitätsmerkmal == schnappschuss.identitätsmerkmal
    }
}
