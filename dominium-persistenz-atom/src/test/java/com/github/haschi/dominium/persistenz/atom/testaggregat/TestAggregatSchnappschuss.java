package com.github.haschi.dominium.persistenz.atom.testaggregat;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.modell.Schnappschuss;

import java.util.UUID;

public final class TestAggregatSchnappschuss implements Schnappschuss<UUID> {

    private static final long serialVersionUID = 3640767131800449347L;

    private final UUID identitätsmerkmal;
    private final Version version;
    private final long wert;

    public TestAggregatSchnappschuss(final UUID identitätsmerkmal, final Version version, final long wert) {
        super();

        this.identitätsmerkmal = identitätsmerkmal;
        this.version = version;
        this.wert = wert;
    }

    public long getWert() {
        return this.wert;
    }

    @Override
    public UUID getIdentitätsmerkmal() {
        return null;
    }

    @Override
    public Version getVersion() {
        return this.version;
    }
}
