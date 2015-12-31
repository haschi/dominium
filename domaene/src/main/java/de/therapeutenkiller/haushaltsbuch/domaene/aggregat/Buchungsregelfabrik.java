package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

final class Buchungsregelfabrik {

    private Buchungsregelfabrik() {
    }

    public static Buchungsregel erzeugen(final Kontoart kontoart, final String kontoname) {
        switch (kontoart) { // NOPMD
            case Ertrag: return new Ertragskontoregel(kontoname);
            default: return new KeineRegel();
        }
    }
}
