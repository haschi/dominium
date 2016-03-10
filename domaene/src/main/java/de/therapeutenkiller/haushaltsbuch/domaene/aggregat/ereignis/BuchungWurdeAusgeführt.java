package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.money.MonetaryAmount;
import java.io.Serializable;

public final class BuchungWurdeAusgeführt extends Wertobjekt implements HaushaltsbuchEreignis, Serializable {

    private static final long serialVersionUID = 1L;

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

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
