package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@ValueObject
public final class KontoWurdeAngelegt extends HaushaltsbuchEreignis implements Serializable {

    private static final long serialVersionUID = 1L;

    public final String kontoname;
    public final Kontoart kontoart;

    protected KontoWurdeAngelegt() {
        super();
        this.kontoname = StringUtils.EMPTY;
        this.kontoart = Kontoart.Aktiv;
    }

    public KontoWurdeAngelegt(final String kontoname, final Kontoart kontoart) {

        super();

        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
