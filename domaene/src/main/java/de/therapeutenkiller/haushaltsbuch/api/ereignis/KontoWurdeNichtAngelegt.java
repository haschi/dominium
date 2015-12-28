package de.therapeutenkiller.haushaltsbuch.api.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import java.util.UUID;

public final class KontoWurdeNichtAngelegt extends Wertobjekt {
    public final UUID haushaltsbuchId;
    public final String kontoname;

    public KontoWurdeNichtAngelegt(final UUID haushaltsbuchId, final String kontoname) {
        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
    }
}
