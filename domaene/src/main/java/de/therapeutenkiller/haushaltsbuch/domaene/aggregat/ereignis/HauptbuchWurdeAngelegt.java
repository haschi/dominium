package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Entity
public final class HauptbuchWurdeAngelegt extends HaushaltsbuchEreignis implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID haushaltsbuchId;

    protected HauptbuchWurdeAngelegt() {
        this.haushaltsbuchId = null;
    }

    public HauptbuchWurdeAngelegt(final UUID haushaltsbuchId) {
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
