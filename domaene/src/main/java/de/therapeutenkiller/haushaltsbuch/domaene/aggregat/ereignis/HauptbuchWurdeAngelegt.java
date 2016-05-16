package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import java.util.UUID;

@ValueObject
public final class HauptbuchWurdeAngelegt implements HaushaltsbuchEreignis  {

    private static final long serialVersionUID = -1916694080894642122L;
    private final UUID haushaltsbuchId;

    protected HauptbuchWurdeAngelegt() {
        super();
        this.haushaltsbuchId = null;
    }

    public HauptbuchWurdeAngelegt(final UUID haushaltsbuchId) {
        super();
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
