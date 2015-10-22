package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.aspekte.CanBeNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.javamoney.moneta.Money;

class Konto {

    private final String kontoname;
    private final Money anfangsbestand;

    public Konto(final String kontoname, final Money anfangsbestand) {

        this.kontoname = kontoname;
        this.anfangsbestand = anfangsbestand;
    }

    public Money bestandBerechnen() {
        return this.anfangsbestand;
    }

    public String getBezeichnung() {
        return this.kontoname;
    }

    @Override public boolean equals(@CanBeNull final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Konto konto = (Konto) obj;

        return new EqualsBuilder()
            .append(this.kontoname, konto.kontoname)
            .append(this.anfangsbestand, konto.anfangsbestand)
            .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.kontoname)
            .append(this.anfangsbestand)
            .toHashCode();
    }
}
