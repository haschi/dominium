package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import javax.money.MonetaryAmount;

/**
 * Created by matthias on 20.12.15.
 */
public abstract class Saldo extends Wertobjekt {

    public abstract MonetaryAmount getBetrag();

    protected final MonetaryAmount betrag;

    public Saldo(final MonetaryAmount betrag) {
        this.betrag = betrag;
    }
}
