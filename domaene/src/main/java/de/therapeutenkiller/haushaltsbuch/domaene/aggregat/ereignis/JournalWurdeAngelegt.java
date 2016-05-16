package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

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
