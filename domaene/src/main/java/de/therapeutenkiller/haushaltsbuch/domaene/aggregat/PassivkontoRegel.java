package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;

public final class PassivkontoRegel implements Buchungsregel {
    private final String kontoname;

    public PassivkontoRegel(final String kontoname) {
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
    public Buchungssatz buchungssatzFürAnfangsbestand(final String kontoname, final MonetaryAmount betrag) {
        return new Buchungssatz(Konto.ANFANGSBESTAND.getBezeichnung(), kontoname, betrag);
    }
}
