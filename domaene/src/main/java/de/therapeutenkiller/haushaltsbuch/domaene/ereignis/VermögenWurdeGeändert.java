package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class VermögenWurdeGeändert extends Domänenereignis {
    private final UUID haushaltsbuchId;
    private final MonetaryAmount vermögen;

    public VermögenWurdeGeändert(final UUID haushaltsbuchId, final MonetaryAmount vermögen) {

        this.haushaltsbuchId = haushaltsbuchId;
        this.vermögen = vermögen;
    }
}
