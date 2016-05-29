package com.github.haschi.dominium.testdomaene;

import com.github.haschi.dominium.modell.Version;

public class Testdaten {

    public static final TestAggregat.Snapshot[] schnappschüsse = {
        schnappschuss(1, 42L),
        schnappschuss(2, 43L),
        schnappschuss(3, 44L)
    };

    public static TestAggregat.Snapshot schnappschuss(final int version, final long payload) {
        return new SnapshotErbauer()
            .version(Version.NEU.nachfolger(version))
            .payload(payload)
            .build();
    }
}
