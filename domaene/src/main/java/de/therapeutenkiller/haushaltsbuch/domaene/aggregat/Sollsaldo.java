package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;

/**
 * Created by matthias on 20.12.15.
 */
public final class Sollsaldo extends Saldo {
    public Sollsaldo(final MonetaryAmount betrag) {
        super(betrag.negate());
    }

    @Override
    public MonetaryAmount getBetrag() {
        return this.betrag.negate();
    }
}
