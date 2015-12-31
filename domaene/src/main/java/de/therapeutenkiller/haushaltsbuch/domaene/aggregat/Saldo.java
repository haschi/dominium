package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;

public abstract class Saldo { // NOPMD TODO Regel ändern

    final MonetaryAmount betrag;

    public abstract MonetaryAmount getBetrag();

    Saldo(final MonetaryAmount betrag) {
        this.betrag = betrag;
    }

    @Override
    public final boolean equals(@DarfNullSein final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Saldo)) {
            return false;
        }

        final Saldo saldo = (Saldo) object;

        return new EqualsBuilder()
                .append(this.betrag, saldo.betrag)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.betrag)
                .toHashCode();
    }
}
