package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

public final class KontoWurdeAngelegt extends Wertobjekt implements HaushaltsbuchEreignis {
    public final String kontoname;
    public final Kontoart kontoart;

    public KontoWurdeAngelegt(final String kontoname, final Kontoart kontoart) {

        super();

        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }

    @Override
    public void applyTo(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }
}
