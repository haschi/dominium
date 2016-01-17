package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.support.Wertobjekt;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public final class KontoWurdeNichtAngelegt extends Wertobjekt implements HaushaltsbuchEreignis, Serializable {

    private static final long serialVersionUID = 1L;

    private final String kontoname;
    private final Kontoart kontoart;

    public KontoWurdeNichtAngelegt(final String kontoname, final Kontoart kontoart) {

        super();

        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }

    @Override
    public void anwendenAuf(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("kontoname", this.kontoname)
                .append("kontoart", this.kontoart)
                .toString();
    }
}
