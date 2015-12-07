package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import javax.money.MonetaryAmount;

public final class BuchungssatzWurdeErstellt {
    private final String von;
    private final String an;
    private final MonetaryAmount betrag;

    public BuchungssatzWurdeErstellt(final String von, final String an, final MonetaryAmount betrag) {

        this.von = von;
        this.an = an;
        this.betrag = betrag;
    }
}
