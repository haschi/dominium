package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import javax.money.MonetaryAmount;

public final class BuchungssatzWurdeErstellt extends Wertobjekt {
    public final String soll;
    public final String haben;
    public final MonetaryAmount betrag;

    public BuchungssatzWurdeErstellt(final String soll, final String haben, final MonetaryAmount betrag) {

        super();

        this.soll = soll;
        this.haben = haben;
        this.betrag = betrag;
    }

    public Buchungssatz getBuchungssatz() {
        return new Buchungssatz(new Konto(this.soll), new Konto(this.haben), this.betrag);
    }
}
