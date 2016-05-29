package com.github.haschi.dominium.testdomaene;

import com.github.haschi.dominium.modell.AggregatFabrik;
import com.github.haschi.dominium.modell.Version;

import java.util.UUID;

public class TestAggregatFabrik
    extends AggregatFabrik<TestAggregat, UUID,  TestAggregatEreignisZiel, TestAggregat.Snapshot> {

    @Override
    public final TestAggregat erzeugen(final UUID identitätsmerkmal) {
        return new TestAggregat(identitätsmerkmal, Version.NEU);
    }
}
