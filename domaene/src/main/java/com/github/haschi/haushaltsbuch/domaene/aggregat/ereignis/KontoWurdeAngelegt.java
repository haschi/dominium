package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import org.apache.commons.lang3.StringUtils;

@ValueObject
public final class KontoWurdeAngelegt implements HaushaltsbuchEreignis {

    private static final long serialVersionUID = 4000453101989445965L;
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
