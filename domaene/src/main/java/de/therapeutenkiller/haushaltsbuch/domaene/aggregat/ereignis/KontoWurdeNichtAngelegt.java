package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public final class KontoWurdeNichtAngelegt extends HaushaltsbuchEreignis implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String kontoname;
    private final Kontoart kontoart;

    public KontoWurdeNichtAngelegt(final String kontoname, final Kontoart kontoart) {

        super();

        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }

    public KontoWurdeNichtAngelegt() {
        super();

        this.kontoname = StringUtils.EMPTY;
        this.kontoart = Kontoart.Aktiv;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("kontoname", this.kontoname)
                .append("kontoart", this.kontoart)
                .toString();
    }
}
