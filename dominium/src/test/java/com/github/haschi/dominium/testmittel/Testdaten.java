package com.github.haschi.dominium.testmittel;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomaene.TestAggregat;

import java.util.UUID;

public class Testdaten {

    public static final long PAYLOAD = 42L;
    public static final Version VERSION = Version.NEU.nachfolger();
    public static final UUID IDENTITÄTSMERKMAL = UUID.randomUUID();

    public static TestAggregat.Snapshot getSchnappschuss() {
        return TestAggregat.Snapshot.from(getAggregat());
    }

    public static TestAggregat getAggregat() {
        final TestAggregat aggregat = new TestAggregat(IDENTITÄTSMERKMAL);
        aggregat.zustandÄndern(PAYLOAD);

        return aggregat;
    }
}
