package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.support.Spezifikation;

public class HabenkontoSpezifikation implements Spezifikation<Buchungssatz> {
    private final Konto konto;

    public HabenkontoSpezifikation(final Konto einKonto) {

        this.konto = einKonto;
    }

    @Override
    public final boolean istErfülltVon(final Buchungssatz buchungssatz) {
        return buchungssatz.hatHabenkonto(this.konto.getBezeichnung()); // NOPMD false positive? TODO
    }
}
