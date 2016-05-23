package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;

@ValueObject
public final class BuchungWurdeAbgelehnt implements HaushaltsbuchEreignis {

    private static final long serialVersionUID = 7359242458848812253L;
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
