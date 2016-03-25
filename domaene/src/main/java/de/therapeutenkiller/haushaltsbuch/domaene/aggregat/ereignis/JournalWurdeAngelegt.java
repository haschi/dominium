package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Entity
public final class JournalWurdeAngelegt extends HaushaltsbuchEreignis implements Serializable {
    private final UUID aktuelleHaushaltsbuchId;

    protected JournalWurdeAngelegt() {
        this.aktuelleHaushaltsbuchId = null;
    }
    
    public JournalWurdeAngelegt(final UUID aktuelleHaushaltsbuchId) {
        this.aktuelleHaushaltsbuchId = aktuelleHaushaltsbuchId;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
