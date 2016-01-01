package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import java.util.UUID;

public final class KontoWurdeAngelegt extends Wertobjekt implements HaushaltsbuchEreignis {
    private final UUID haushaltsbuchId;
    public final String kontoname;
    public final Kontoart kontoart;

    public KontoWurdeAngelegt(final UUID haushaltsbuchId, final String kontoname, final Kontoart kontoart) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }

    @Override
    public void applyTo(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }
}
