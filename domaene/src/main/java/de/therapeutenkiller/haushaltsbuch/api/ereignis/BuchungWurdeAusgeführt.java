package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import javax.money.MonetaryAmount;

public final class BuchungWurdeAusgeführt extends Wertobjekt {
    public final String soll;
    public final String haben;
    public final MonetaryAmount betrag;

    public BuchungWurdeAusgeführt(final String soll, final String haben, final MonetaryAmount betrag) {

        super();

        this.soll = soll;
        this.haben = haben;
        this.betrag = betrag;
    }

    public Buchungssatz getBuchungssatz() {
        return new Buchungssatz(this.soll, this.haben, this.betrag);
    }
}
