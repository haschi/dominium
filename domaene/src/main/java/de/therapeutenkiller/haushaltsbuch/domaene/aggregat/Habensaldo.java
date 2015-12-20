package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;

/**
 * Created by matthias on 20.12.15.
 */
public final class Habensaldo extends Saldo {
    @Override
    public MonetaryAmount getBetrag() {
        return this.betrag;
    }

    public Habensaldo(final MonetaryAmount betrag) {
        super(betrag);
    }
}
