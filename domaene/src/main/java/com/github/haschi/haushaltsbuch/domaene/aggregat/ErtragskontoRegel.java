package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontoname;

import javax.money.MonetaryAmount;

public final class ErtragskontoRegel implements Buchungsregel {

    private final Kontoname konto;

    public ErtragskontoRegel(final Kontoname konto) {
        super();

        this.konto = konto;
    }

    @Override
    public boolean kannErtragBuchen(final Buchungssatz buchungssatz) {
        return !buchungssatz.getSollkonto().equals(this.konto);
    }

    @Override
    public boolean kannVerlustBuchen(final Buchungssatz buchungssatz) {
        return false;
    }

    @Override
    public Buchungssatz buchungssatzFürAnfangsbestand(final Konto konto, final MonetaryAmount betrag) {
        return new Buchungssatz(konto.getName(), Konto.ANFANGSBESTAND.getName(), betrag);
    }
}
