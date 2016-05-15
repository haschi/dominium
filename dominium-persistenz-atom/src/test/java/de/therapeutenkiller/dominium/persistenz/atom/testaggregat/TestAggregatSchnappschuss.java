package de.therapeutenkiller.dominium.persistenz.atom.testaggregat;

import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.UUID;

public final class TestAggregatSchnappschuss implements Schnappschuss<TestAggregat, UUID> {

    private static final long serialVersionUID = 3640767131800449347L;

    private final UUID identitätsmerkmal;
    private final long version;
    private long wert;

    public TestAggregatSchnappschuss(final UUID identitätsmerkmal, final long version, final long wert) {

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
    public long getVersion() {
        return 0;
    }
}
