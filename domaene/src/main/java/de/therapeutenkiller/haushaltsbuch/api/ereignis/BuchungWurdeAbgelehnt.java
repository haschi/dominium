package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import java.io.Serializable;

public final class BuchungWurdeAbgelehnt extends Wertobjekt  implements HaushaltsbuchEreignis, Serializable {
    private static final long serialVersionUID = 1L;

    private final String grund;

    public BuchungWurdeAbgelehnt(final String grund) {

        super();
        this.grund = grund;
    }

    @Override
    public String toString() {
        return String.format("Buchung wurde nicht ausgeführt: %s", this.grund); // NOPMD LoD TODO
    }

    @Override
    public void applyTo(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }
}
