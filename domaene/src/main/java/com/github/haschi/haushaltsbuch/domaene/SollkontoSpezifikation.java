package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.dominium.aggregat.Spezifikation;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Buchungssatz;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto;

public final class SollkontoSpezifikation implements Spezifikation<Buchungssatz> {

    private final Konto konto;

    public SollkontoSpezifikation(final Konto konto) {
        super();

        this.konto = konto;
    }

    @Override
    public boolean istErfülltVon(final Buchungssatz buchungssatz) {

        return buchungssatz.hatSollkonto(this.konto.getName()); // NOPMD false positive? TODO
    }
}
