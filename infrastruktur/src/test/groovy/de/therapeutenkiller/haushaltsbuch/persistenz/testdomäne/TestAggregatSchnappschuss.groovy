package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne

import de.therapeutenkiller.dominium.aggregat.Schnappschuss


class TestAggregatSchnappschuss implements Schnappschuss<TestAggregat, UUID> {

    long version
    long payload
    UUID identitätsmerkmal
}
