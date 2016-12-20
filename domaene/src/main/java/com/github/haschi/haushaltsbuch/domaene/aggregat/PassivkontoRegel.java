package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;

import javax.money.MonetaryAmount;

public final class PassivkontoRegel
        implements Buchungsregel
{
    private final Kontobezeichnung kontobezeichnung;

    public PassivkontoRegel(final Kontobezeichnung kontobezeichnung)
    {
        super();
        this.kontobezeichnung = kontobezeichnung;
    }

    @Override
    public boolean kannErtragBuchen(final Buchungssatz buchungssatz)
    {
        return false;
    }

    @Override
    public boolean kannVerlustBuchen(final Buchungssatz buchungssatz)
    {
        return buchungssatz.getSollkonto().equals(this.kontobezeichnung);
    }

    @Override
    public Buchungssatz buchungssatzFürAnfangsbestand(final Konto konto, final MonetaryAmount betrag)
    {
        return new Buchungssatz(Konto.ANFANGSBESTAND.getName(), konto.getName(), betrag);
    }
}
