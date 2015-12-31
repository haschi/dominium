package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

final class BuchungsregelFabrik {

    private BuchungsregelFabrik() {
    }

    public static Buchungsregel erzeugen(final Kontoart kontoart, final String kontoname) {
        switch (kontoart) { // NOPMD
            case Ertrag: return new ErtragskontoRegel(kontoname);
            default: return new KeineRegel();
        }
    }
}
