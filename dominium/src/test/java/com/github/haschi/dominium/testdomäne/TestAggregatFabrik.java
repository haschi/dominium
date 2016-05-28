package com.github.haschi.dominium.testdomäne;

import com.github.haschi.dominium.modell.AggregatFabrik;
import com.github.haschi.dominium.modell.Version;

import java.util.UUID;

public class TestAggregatFabrik
    extends AggregatFabrik<TestAggregat, TestAggregatSchnappschuss, TestAggregatEreignisZiel, UUID> {

    @Override
    public final TestAggregat erzeugen(final UUID identitätsmerkmal) {
        return new TestAggregat(identitätsmerkmal, Version.NEU);
    }
}
