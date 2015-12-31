package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

public final class ErtragskontoRegel implements Buchungsregel {

    private final String konto;

    public ErtragskontoRegel(final String konto) {

        this.konto = konto;
    }

    @Override
    public boolean kannErtragBuchen(final Buchungssatz buchungssatz) {
        return !buchungssatz.getSollkonto().equals(this.konto); // NOPMD LoD TODO
    }
}
