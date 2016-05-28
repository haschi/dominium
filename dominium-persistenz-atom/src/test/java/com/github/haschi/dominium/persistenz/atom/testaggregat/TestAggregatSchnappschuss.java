package com.github.haschi.dominium.persistenz.atom.testaggregat;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.modell.Schnappschuss;

public final class TestAggregatSchnappschuss implements Schnappschuss {

    private static final long serialVersionUID = 3640767131800449347L;

    private final Version version;
    private final long wert;

    public TestAggregatSchnappschuss(final Version version, final long wert) {
        super();

        this.version = version;
        this.wert = wert;
    }

    public long getWert() {
        return this.wert;
    }

    @Override
    public Version getVersion() {
        return this.version;
    }
}
