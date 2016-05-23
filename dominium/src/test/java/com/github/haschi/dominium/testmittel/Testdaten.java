package com.github.haschi.dominium.testmittel;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomäne.TestAggregatSchnappschuss;

import java.util.UUID;

public class Testdaten {

    public static final long PAYLOAD = 42L;
    public static final Version VERSION = Version.NEU.nachfolger();

    public static TestAggregatSchnappschuss getSchnappschuss(final UUID identitätsmerkmal) {
        return TestAggregatSchnappschuss.builder()
            .identitätsmerkmal(identitätsmerkmal)
            .payload(PAYLOAD)
            .version(VERSION)
            .build();
    }
}
