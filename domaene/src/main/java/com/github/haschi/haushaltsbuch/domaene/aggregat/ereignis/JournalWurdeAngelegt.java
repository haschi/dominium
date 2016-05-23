package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import java.util.UUID;

@ValueObject
public final class JournalWurdeAngelegt implements HaushaltsbuchEreignis {

    private static final long serialVersionUID = 274297407457888074L;
    private final UUID aktuelleHaushaltsbuchId;

    protected JournalWurdeAngelegt() {
        super();
        this.aktuelleHaushaltsbuchId = null;
    }
    
    public JournalWurdeAngelegt(final UUID aktuelleHaushaltsbuchId) {
        super();
        this.aktuelleHaushaltsbuchId = aktuelleHaushaltsbuchId;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
