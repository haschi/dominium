package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

final class BuchungsregelFabrik {

    private final Kontoart kontoart;

    BuchungsregelFabrik(final Kontoart kontoart) {
        super();
        this.kontoart = kontoart;
    }

    Buchungsregel erzeugen(final String kontoname) {
        switch (this.kontoart) {
            case Ertrag: return new ErtragskontoRegel(kontoname);
            case Passiv: return new PassivkontoRegel(kontoname);
            default: return new KeineRegel();
        }
    }
}
