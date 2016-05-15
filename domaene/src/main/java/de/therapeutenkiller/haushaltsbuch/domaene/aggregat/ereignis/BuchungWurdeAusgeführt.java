package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import java.io.Serializable;

@ValueObject
public final class BuchungWurdeAusgeführt extends HaushaltsbuchEreignis implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Buchungssatz buchungssatz;

    public BuchungWurdeAusgeführt(final Buchungssatz buchungssatz) {
        super();
        this.buchungssatz = buchungssatz;
    }

    public Buchungssatz getBuchungssatz() {
        return this.buchungssatz;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
