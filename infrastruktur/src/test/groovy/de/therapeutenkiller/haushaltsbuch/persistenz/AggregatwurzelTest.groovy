package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.Aggregatwurzel
import spock.lang.Specification

class AggregatwurzelTest extends Specification {

    class TestAggregat extends Aggregatwurzel<TestAggregat, UUID> {

        protected TestAggregat(UUID identitätsmerkmal) {
            super(identitätsmerkmal)
        }

        @Override
        protected TestAggregat getSelf() {
            return this
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
}
