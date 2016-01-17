package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.dominium.aggregat.Initialereignis;
import de.therapeutenkiller.dominium.aggregat.Wertobjekt;

import java.io.Serializable;
import java.util.UUID;

public final class HaushaltsbuchWurdeAngelegt
        extends Wertobjekt
        implements HaushaltsbuchEreignis, Initialereignis<Haushaltsbuch, UUID>, Serializable {

    private static final long serialVersionUID = 1L;

    public final UUID haushaltsbuchId;

    public HaushaltsbuchWurdeAngelegt(final UUID haushaltsbuchId) {
        super();
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void anwendenAuf(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }

    @Override
    public UUID getIdentitätsmerkmal() {
        return this.haushaltsbuchId;
    }
}
