package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import javax.money.MonetaryAmount;

/**
 * Created by matthias on 20.12.15.
 */
public abstract class Saldo extends Wertobjekt { // NOPMD TODO Regel ändern

    protected final MonetaryAmount betrag;

    public abstract MonetaryAmount getBetrag();

    public Saldo(final MonetaryAmount betrag) {
        super();
        this.betrag = betrag;
    }
}
