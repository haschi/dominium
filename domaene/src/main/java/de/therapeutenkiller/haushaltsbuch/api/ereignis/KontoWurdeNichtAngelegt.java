package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Haushaltsbuchereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

public final class KontoWurdeNichtAngelegt extends Wertobjekt implements Haushaltsbuchereignis {
    public final String kontoname;
    public final Kontoart kontoart;

    public KontoWurdeNichtAngelegt(final String kontoname, final Kontoart kontoart) {

        super();

        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }

    @Override
    public void applyTo(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }
}
