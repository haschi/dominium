package de.therapeutenkiller.dominium.persistenz.atom.testaggregat;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.modell.Version;

import java.util.UUID;

public final class TestAggregatSchnappschuss implements Schnappschuss<UUID> {

    private static final long serialVersionUID = 3640767131800449347L;

    private final UUID identitätsmerkmal;
    private final Version version;
    private long wert;

    public TestAggregatSchnappschuss(final UUID identitätsmerkmal, final Version version, final long wert) {

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
