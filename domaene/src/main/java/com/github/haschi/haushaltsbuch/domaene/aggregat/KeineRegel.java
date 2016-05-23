package com.github.haschi.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;

public final class KeineRegel implements Buchungsregel {
    @Override
    public boolean kannErtragBuchen(final Buchungssatz buchungssatz) {
        return true;
    }

    @Override
    public boolean kannVerlustBuchen(final Buchungssatz buchungssatz) {
        return true;
    }

    @Override
    public Buchungssatz buchungssatzFürAnfangsbestand(final String kontoname, final MonetaryAmount betrag) {
        return new Buchungssatz(kontoname, Konto.ANFANGSBESTAND.getBezeichnung(), betrag);
    }
}
