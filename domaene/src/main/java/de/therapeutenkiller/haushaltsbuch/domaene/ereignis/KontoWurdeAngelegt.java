package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import java.util.UUID;

/**
 * Created by matthias on 17.12.15.
 */
public final class KontoWurdeAngelegt extends Wertobjekt {
    private final UUID haushaltsbuchId;
    private final String kontoname;

    public KontoWurdeAngelegt(final UUID haushaltsbuchId, final String kontoname) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
    }
}
