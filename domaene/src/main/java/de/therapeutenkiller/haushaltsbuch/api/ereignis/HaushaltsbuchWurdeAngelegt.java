package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Haushaltsbuchereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import java.util.UUID;

public final class HaushaltsbuchWurdeAngelegt extends Wertobjekt implements Haushaltsbuchereignis {

    public final UUID haushaltsbuchId;

    public HaushaltsbuchWurdeAngelegt(final UUID haushaltsbuchId) {
        super();
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void applyTo(final Haushaltsbuch spiel) {
        spiel.falls(this);
    }
}
