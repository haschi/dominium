package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;

public class Buchungssatz {

    private final Konto sollkonto;
    private final Konto habenkonto;
    private final MonetaryAmount währungsbetrag;

    public Buchungssatz(final Konto sollkonto, final Konto habenkonto, final MonetaryAmount währungsbetrag) {
        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }

    public final Konto getSollkonto() {
        return this.sollkonto;
    }

    public final Konto getHabenkonto() {
        return this.habenkonto;
    }

    public final MonetaryAmount getWährungsbetrag() {
        return this.währungsbetrag;
    }

    @Override
    public final boolean equals(@DarfNullSein final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Buchungssatz that = (Buchungssatz) obj;

        return new EqualsBuilder()
            .append(this.sollkonto, that.sollkonto)
            .append(this.habenkonto, that.habenkonto)
            .append(this.währungsbetrag, that.währungsbetrag)
            .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.sollkonto)
            .append(this.habenkonto)
            .append(this.währungsbetrag)
            .toHashCode();
    }
}
