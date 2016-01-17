package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne

import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.Schnappschuss

/**
 * Created by matthias on 17.01.16.
 */
class TestAggregatSchnappschuss implements Schnappschuss<TestAggregat, UUID> {

    long version
    long payload
    UUID identitätsmerkmal

    @Override
    TestAggregat materialisieren() {
        new TestAggregat(this)
    }
}
