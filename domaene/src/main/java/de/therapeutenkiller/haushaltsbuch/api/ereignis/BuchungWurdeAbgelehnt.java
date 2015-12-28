package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

public final class BuchungWurdeAbgelehnt extends Wertobjekt {
    public final String grund;

    public BuchungWurdeAbgelehnt(final String grund) {

        super();
        this.grund = grund;
    }

    @Override
    public String toString() {
        return String.format("Buchung wurde nicht ausgeführt: %s", this.grund); // NOPMD LoD TODO
    }
}
