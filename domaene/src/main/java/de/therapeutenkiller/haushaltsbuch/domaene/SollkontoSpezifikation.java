package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Spezifikation;

/**
 * Created by matthias on 18.12.15.
 */
public final class SollkontoSpezifikation implements Spezifikation<Buchungssatz> {

    private final Konto konto;

    public SollkontoSpezifikation(final Konto konto) {

        this.konto = konto;
    }

    @Override
    public boolean istErfülltVon(final Buchungssatz buchungssatz) {

        return buchungssatz.sollst(this.konto.getBezeichnung()); // NOPMD false positive? TODO
    }
}
