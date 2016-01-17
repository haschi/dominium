package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.Aggregatwurzel
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.Domänenereignis
import spock.lang.Specification

class AggregatwurzelTest extends Specification {

    class TestAggregat extends Aggregatwurzel<TestAggregat, UUID> {

        long zustand

        protected TestAggregat(UUID identitätsmerkmal) {
            super(identitätsmerkmal)
        }

        @Override
        protected TestAggregat getSelf() {
            return this
        }

        public void zustandÄndern(long payload) {
            bewirkt( new ZustandWurdeGeändert(payload))
        }

        void falls(ZustandWurdeGeändert zustandWurdeGeändert) {
            this.zustand = zustandWurdeGeändert.payload
        }
    }

    class ZustandWurdeGeändert extends  Wertobjekt implements Domänenereignis<TestAggregat> {

        private final long payload

        ZustandWurdeGeändert(long payload) {

            this.payload = payload
        }

        @Override
        void anwendenAuf(TestAggregat aggregat) {
            aggregat.falls(this)
        }
    }

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
        TestAggregat aggregat = new TestAggregat(UUID.randomUUID());

        when: "ich eine Änderung am Aggregat durchführe"
        aggregat.zustandÄndern(42L)

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
}
