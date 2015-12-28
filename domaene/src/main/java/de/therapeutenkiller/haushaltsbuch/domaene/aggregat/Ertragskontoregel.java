package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

/**
 * Created by matthias on 28.12.15.
 */
public final class Ertragskontoregel implements Buchungsregel {

    private final String konto;

    public Ertragskontoregel(final String konto) {

        this.konto = konto;
    }

    @Override
    public boolean kannAusgabeBuchen(final Buchungssatz buchungssatz) {
        return !buchungssatz.getSollkonto().equals(this.konto); // NOPMD LoD TODO
    }
}
