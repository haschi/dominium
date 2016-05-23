package com.github.haschi.dominium.modell;

import com.github.haschi.coding.aspekte.DarfNullSein;

import java.io.Serializable;

public final class Version implements Comparable<Version>, Serializable {

    public static final Version NEU = new Version(0L);
    public static final Version MAX = new Version(Long.MAX_VALUE);

    private static final long serialVersionUID = -294764863038542923L;

    public final long version;

    public Version() {
        super();
        this.version = 0L;
    }

    public Version(final long version) {
        super();

        if (version < 0L) {
            throw new IllegalArgumentException("Version ist kleiner als 0L");
        }

        this.version = version;
    }

    public Version nachfolger() {
        if (this.equals(MAX)) {
            throw new IllegalStateException("Version-Überlauf");
        }

        return new Version(this.version + 1);
    }

    public long alsLong() {
        return this.version;
    }

    @Override
    public boolean equals(@DarfNullSein final Object anderes) {
        if (this == anderes) {
            return true;
        }

        if (!(anderes instanceof Version)) {
            return false;
        }

        final Version version1 = (Version) anderes;

        return this.version == version1.version;
    }

    @Override
    public int hashCode() {
        return (int) (this.version ^ (this.version >>> 32));
    }

    @Override
    public int compareTo(final Version version) {
        return Long.compare(this.version, version.version);
    }
}
