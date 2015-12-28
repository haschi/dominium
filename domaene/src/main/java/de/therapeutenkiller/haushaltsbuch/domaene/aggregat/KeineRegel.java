package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

/**
 * Created by matthias on 28.12.15.
 */
public final class KeineRegel implements Buchungsregel {
    @Override
    public boolean kannAusgabeBuchen(final Buchungssatz buchungssatz) {
        return true;
    }
}
