package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Buchungssatz;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto;
import com.github.haschi.dominium.aggregat.Spezifikation;

public class HabenkontoSpezifikation implements Spezifikation<Buchungssatz> {
    private final Konto konto;

    public HabenkontoSpezifikation(final Konto einKonto) {
        super();

        this.konto = einKonto;
    }

    @Override
    public final boolean istErfülltVon(final Buchungssatz buchungssatz) {
        return buchungssatz.hatHabenkonto(this.konto.getBezeichnung()); // NOPMD false positive? TODO
    }
}