package de.therapeutenkiller.dominium.modell.testdomäne

import de.therapeutenkiller.dominium.modell.Schnappschuss
import groovy.transform.builder.Builder
import jdk.nashorn.internal.ir.annotations.Immutable

@Builder
@Immutable
class TestAggregatSchnappschuss implements Schnappschuss<TestAggregat, UUID> {

    long version;
    long payload;
    UUID identitätsmerkmal;

    @Override
    TestAggregat wiederherstellen() {
        return new TestAggregat(this)
    }
}
