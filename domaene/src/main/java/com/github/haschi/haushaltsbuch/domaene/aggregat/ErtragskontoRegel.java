package com.github.haschi.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;

public final class ErtragskontoRegel implements Buchungsregel {

    private final String konto;

    public ErtragskontoRegel(final String konto) {
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
    public Buchungssatz buchungssatzFürAnfangsbestand(final String kontoname, final MonetaryAmount betrag) {
        return new Buchungssatz(kontoname, Konto.ANFANGSBESTAND.getBezeichnung(), betrag);
    }
}
