package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Spezifikation;

public final class SollkontoSpezifikation implements Spezifikation<Buchungssatz> {

    private final Konto konto;

    public SollkontoSpezifikation(final Konto konto) {

        this.konto = konto;
    }

    @Override
    public boolean istErfülltVon(final Buchungssatz buchungssatz) {

        return buchungssatz.hatSollkonto(this.konto.getBezeichnung()); // NOPMD false positive? TODO
    }
}
