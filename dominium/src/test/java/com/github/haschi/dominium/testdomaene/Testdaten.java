package com.github.haschi.dominium.testdomaene;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomaene.TestAggregat.TestAggregatSchnapp;

public class Testdaten {

    public static final TestAggregatSchnapp[] schnappschüsse = {
        schnappschuss(1, 42L),
        schnappschuss(2, 43L),
        schnappschuss(3, 44L)
    };

    public static TestAggregatSchnapp schnappschuss(final int version, final long payload) {
        return new TestAggregatSchnappErbauer()
            .version(Version.NEU.nachfolger(version))
            .payload(payload)
            .build();
    }
}
