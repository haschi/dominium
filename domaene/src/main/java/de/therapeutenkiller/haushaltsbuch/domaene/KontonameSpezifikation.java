package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.dominium.aggregat.Spezifikation;

public final class KontonameSpezifikation implements Spezifikation<Konto> {
    private final String kontoname;

    public KontonameSpezifikation(final String kontoname) {
        this.kontoname = kontoname;
    }

    @Override
    public boolean istErfülltVon(final Konto konto) {
        return konto.getBezeichnung().equals(this.kontoname); // NOPMD TODO
    }
}
