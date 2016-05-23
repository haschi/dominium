package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Buchungssatz;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

@ValueObject
public final class BuchungWurdeAusgeführt implements HaushaltsbuchEreignis {

    private static final long serialVersionUID = 840178583178747792L;
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
