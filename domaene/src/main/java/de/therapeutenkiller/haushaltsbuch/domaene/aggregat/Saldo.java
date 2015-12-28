package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;

/**
 * Created by matthias on 20.12.15.
 */
public abstract class Saldo { // NOPMD TODO Regel ändern

    private final MonetaryAmount betrag;

    public MonetaryAmount getBetrag() {
        return this.betrag;
    }

    public Saldo( final MonetaryAmount betrag) {
        this.betrag = betrag;
    }

    @Override
    public boolean equals(@DarfNullSein Object o) {
        if (this == o) return true;

        if (!(o instanceof Saldo)) return false;

        Saldo saldo = (Saldo) o;

        return new EqualsBuilder()
                .append(betrag, saldo.betrag)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(betrag)
                .toHashCode();
    }
}
