package de.therapeutenkiller.dominium.modell;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Versionsbereich {

    private final long von;
    private final long bis;

    public static final Versionsbereich ALLE_VERSIONEN = new Versionsbereich(0L, Long.MAX_VALUE);

    private Versionsbereich(final long von, final long bis) {
        super();

        if (von > bis) {
            throw new IllegalArgumentException();
        }

        if (von < 0L) {
            throw new IllegalArgumentException();
        }

        this.von = von;
        this.bis = bis;
    }

    // tbd: Der Konstruktor muss private sein. Der Aspekt NullReferenzPrüfung ist fehlerhaft.
    public Versionsbereich(final VersionsbereichBuilder versionsbereichBuilder) {
        this(versionsbereichBuilder.von, versionsbereichBuilder.bis);
    }

    public boolean liegtInnerhalb(final long zahl) {
        return (this.von <= zahl) && (zahl <= this.bis);
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

    public long getVon() {
        return this.von;
    }

    public long getBis() {
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
