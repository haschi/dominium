package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.Kontoname;

final class BuchungsregelFabrik {

    private final Kontoart kontoart;

    BuchungsregelFabrik(final Kontoart kontoart) {
        super();
        this.kontoart = kontoart;
    }

    Buchungsregel erzeugen(final Kontoname kontoname) {
        switch (this.kontoart) {
            case Ertrag: return new ErtragskontoRegel(kontoname);
            case Passiv: return new PassivkontoRegel(kontoname);
            default: return new KeineRegel();
        }
    }
}
