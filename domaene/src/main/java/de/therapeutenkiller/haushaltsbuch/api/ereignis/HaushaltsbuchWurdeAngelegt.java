package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Initialereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import java.util.UUID;

public final class HaushaltsbuchWurdeAngelegt
        extends Wertobjekt
        implements HaushaltsbuchEreignis, Initialereignis<UUID, Haushaltsbuch> {

    public final UUID haushaltsbuchId;

    public HaushaltsbuchWurdeAngelegt(final UUID haushaltsbuchId) {
        super();
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void applyTo(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }

    @Override
    public UUID getIdentitätsmerkmal() {
        return this.haushaltsbuchId;
    }
}
