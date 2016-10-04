package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.dominium.aggregat.Spezifikation;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Buchungssatz;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto;

public final class HabenkontoSpezifikation
        implements Spezifikation<Buchungssatz>
{
    private final Konto konto;

    public HabenkontoSpezifikation(final Konto einKonto)
    {
        super();

        this.konto = einKonto;
    }

    @Override
    public final boolean istErfülltVon(final Buchungssatz buchungssatz)
    {
        return buchungssatz.hatHabenkonto(this.konto.getName()); // NOPMD false positive? TODO
    }
}
