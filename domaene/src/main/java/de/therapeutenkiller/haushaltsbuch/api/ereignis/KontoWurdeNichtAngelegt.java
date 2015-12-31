package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Haushaltsbuchereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import java.util.UUID;

public final class KontoWurdeNichtAngelegt extends Wertobjekt implements Haushaltsbuchereignis {
    public final String kontoname;
    public final Kontoart kontoart;
    private final UUID haushaltsbuchId;

    public KontoWurdeNichtAngelegt(final UUID identität, final String kontoname, final Kontoart kontoart) {

        super();

        this.haushaltsbuchId = identität;
        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }

    @Override
    public void applyTo(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }
}
