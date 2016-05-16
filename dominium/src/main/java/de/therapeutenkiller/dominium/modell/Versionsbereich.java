package de.therapeutenkiller.dominium.modell;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Versionsbereich {

    private final Version von;
    private final Version bis;

    public static final Versionsbereich ALLE_VERSIONEN = new Versionsbereich(Version.NEU, Version.MAX);

    private Versionsbereich(final Version von, final Version bis) {
        super();

        if (von.compareTo(bis) > 0) {
            throw new IllegalArgumentException();
        }

        if (von.compareTo(Version.NEU) < 0) {
            throw new IllegalArgumentException();
        }

        this.von = von;
        this.bis = bis;
    }

    // tbd: Der Konstruktor muss private sein. Der Aspekt NullReferenzPrüfung ist fehlerhaft.
    public Versionsbereich(final VersionsbereichBuilder versionsbereichBuilder) {
        this(new Version(versionsbereichBuilder.von), new Version(versionsbereichBuilder.bis));
    }

    public boolean liegtInnerhalb(final long zahl) {
        return (this.von.compareTo(new Version(zahl)) < 1) && (new Version(zahl).compareTo(this.bis) < 1);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Versionsbereich)) {
            return false;
        }

        final Versionsbereich that = (Versionsbereich) object;

        return new EqualsBuilder()
                .append(this.von, that.von)
                .append(this.bis, that.bis)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.von)
                .append(this.bis)
                .toHashCode();
    }

    public Version getVon() {
        return this.von;
    }

    public Version getBis() {
        return this.bis;
    }

    public static VersionsbereichBuilderInterface von(final long wert) {
        return new VersionsbereichBuilder().von(wert);
    }

    private static class VersionsbereichBuilder implements VersionsbereichBuilderInterface {

        private long von;
        private long bis;

        private VersionsbereichBuilderInterface von(final long wert) {
            this.von = wert;
            return this;
        }

        public Versionsbereich bis(final long wert) {
            this.bis = wert;
            return new Versionsbereich(this);
        }
    }
}
