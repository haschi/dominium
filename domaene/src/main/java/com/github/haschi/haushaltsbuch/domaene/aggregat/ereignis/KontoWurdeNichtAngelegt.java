package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

@ValueObject
public final class KontoWurdeNichtAngelegt implements HaushaltsbuchEreignis {

    private static final long serialVersionUID = -6162988326427084703L;
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
