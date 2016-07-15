package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontoname;

import javax.money.MonetaryAmount;

public final class PassivkontoRegel implements Buchungsregel {
    private final Kontoname kontoname;

    public PassivkontoRegel(final Kontoname kontoname) {
        super();
        this.kontoname = kontoname;
    }

    @Override
    public boolean kannErtragBuchen(final Buchungssatz buchungssatz) {
        return false;
    }

    @Override
    public boolean kannVerlustBuchen(final Buchungssatz buchungssatz) {
        return buchungssatz.getSollkonto().equals(this.kontoname);
    }

    @Override
    public Buchungssatz buchungssatzFürAnfangsbestand(final Konto kontoname, final MonetaryAmount betrag) {
        return new Buchungssatz(Konto.ANFANGSBESTAND.getName(), kontoname.getName(), betrag);
    }
}
