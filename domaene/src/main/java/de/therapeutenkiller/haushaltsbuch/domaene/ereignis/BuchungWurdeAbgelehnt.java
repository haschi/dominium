package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

public final class BuchungWurdeAbgelehnt extends Wertobjekt {
    public final String fehlermeldung;

    public BuchungWurdeAbgelehnt(final String fehlermeldung) {

        super();
        this.fehlermeldung = fehlermeldung;
    }

    @Override
    public String toString() {
        return String.format("Buchung wurde nicht ausgeführt: %s", this.fehlermeldung); // NOPMD LoD TODO
    }
}
