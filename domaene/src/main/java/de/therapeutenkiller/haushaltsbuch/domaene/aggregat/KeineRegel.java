package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

public final class KeineRegel implements Buchungsregel {
    @Override
    public boolean kannErtragBuchen(final Buchungssatz buchungssatz) {
        return true;
    }
}
