package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@ValueObject(exclude = {"id"})
public final class BuchungWurdeAbgelehnt extends HaushaltsbuchEreignis implements Serializable {
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
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
