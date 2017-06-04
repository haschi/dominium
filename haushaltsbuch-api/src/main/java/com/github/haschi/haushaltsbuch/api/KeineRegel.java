package com.github.haschi.haushaltsbuch.api;

import javax.money.MonetaryAmount;

public final class KeineRegel
        implements Buchungsregel
{
    @Override
    public boolean kannErtragBuchen(final Buchungssatz buchungssatz)
    {
        return true;
    }

    @Override
    public boolean kannVerlustBuchen(final Buchungssatz buchungssatz)
    {
        return true;
    }

    @Override
    public Buchungssatz buchungssatzFürAnfangsbestand(final Konto konto, final MonetaryAmount betrag)
    {
        return new Buchungssatz(konto.getName(), Konto.ANFANGSBESTAND.getName(), betrag);
    }
}
