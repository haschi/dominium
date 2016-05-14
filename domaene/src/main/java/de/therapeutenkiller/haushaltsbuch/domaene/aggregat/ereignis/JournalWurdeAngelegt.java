package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Entity
@ValueObject(exclude = {"id"})
public final class JournalWurdeAngelegt extends HaushaltsbuchEreignis implements Serializable {
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
