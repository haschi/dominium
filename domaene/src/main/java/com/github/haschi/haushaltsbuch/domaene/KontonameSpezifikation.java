package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto;
import com.github.haschi.dominium.aggregat.Spezifikation;

public final class KontonameSpezifikation implements Spezifikation<Konto> {
    private final String kontoname;

    public KontonameSpezifikation(final String kontoname) {
        super();
        this.kontoname = kontoname;
    }

    @Override
    public boolean istErfülltVon(final Konto konto) {
        return konto.getBezeichnung().equals(this.kontoname); // NOPMD TODO
    }
}
