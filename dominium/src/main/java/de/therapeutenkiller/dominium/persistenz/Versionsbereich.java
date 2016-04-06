package de.therapeutenkiller.dominium.persistenz;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Versionsbereich {

    private final long von;
    private final long bis;

    public static final Versionsbereich ALLE_VERSIONEN = new Versionsbereich(1L, Long.MAX_VALUE);

    public Versionsbereich(final long von, final long bis) {
        super();

        if (von > bis) {
            throw new IllegalArgumentException();
        }

        if (von < 1L) {
            throw new IllegalArgumentException();
        }

        this.von = von;
        this.bis = bis;
    }

    public boolean liegtInnerhalb(final long zahl) {
        return this.von <= zahl && zahl <= this.bis;
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
}
