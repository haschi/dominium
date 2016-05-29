package com.github.haschi.dominium.testmittel;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomaene.TestAggregat;
import com.github.haschi.dominium.testdomaene.TestAggregat.TestAggregatSchnapp;

import java.util.UUID;

public class Testdaten {

    public static final long PAYLOAD = 42L;
    public static final Version VERSION = Version.NEU.nachfolger();
    public static final UUID IDENTITÄTSMERKMAL = UUID.randomUUID();

    public static TestAggregatSchnapp getSchnappschuss() {
        return TestAggregatSchnapp.from(getAggregat());
    }

    public static TestAggregat getAggregat() {
        final TestAggregat aggregat = new TestAggregat(IDENTITÄTSMERKMAL, VERSION);
        aggregat.zustandÄndern(PAYLOAD);

        return aggregat;
    }
}
