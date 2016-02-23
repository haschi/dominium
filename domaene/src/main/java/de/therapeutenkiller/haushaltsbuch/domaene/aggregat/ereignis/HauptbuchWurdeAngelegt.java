package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;

import java.util.UUID;

public final class HauptbuchWurdeAngelegt extends Wertobjekt implements HaushaltsbuchEreignis {
    private static final long serialVersionUID = 1L;

    private UUID haushaltsbuchId;

    public HauptbuchWurdeAngelegt(final UUID haushaltsbuchId) {
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void anwendenAuf(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }
}
