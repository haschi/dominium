package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;

public final class Konto {

    private final String kontoname;
    private final MonetaryAmount anfangsbestand;

    public Konto(final String kontoname, final MonetaryAmount anfangsbestand) {

        this.kontoname = kontoname;
        this.anfangsbestand = anfangsbestand;
    }

    public MonetaryAmount bestandBerechnen() {
        return this.anfangsbestand;
    }

    public String getBezeichnung() {
        return this.kontoname;
    }

    @Override public boolean equals(@DarfNullSein final Object obj) {
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
