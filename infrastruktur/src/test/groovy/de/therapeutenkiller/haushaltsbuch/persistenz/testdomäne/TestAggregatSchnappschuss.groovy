package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne

import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.Schnappschuss

class TestAggregatSchnappschuss implements Schnappschuss<TestAggregat, UUID> {

    long version
    long payload
    UUID identitätsmerkmal
}
