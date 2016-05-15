package de.therapeutenkiller.dominium.modell;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

public final class Version implements Comparable<Version> {

    public static final Version NEU = new Version(0L);
    public static final Version MAX = new Version(Long.MAX_VALUE);

    private final long version;

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
