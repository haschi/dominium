package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import javax.money.MonetaryAmount;
import java.util.UUID;

public final class VermögenWurdeGeändert {

    public UUID getHaushaltsbuchId() {
        return this.haushaltsbuchId;
    }

    private final UUID haushaltsbuchId;

    public MonetaryAmount getVermögen() {
        return this.vermögen;
    }

    private final MonetaryAmount vermögen;

    public VermögenWurdeGeändert(final UUID haushaltsbuchId, final MonetaryAmount vermögen) {

        this.haushaltsbuchId = haushaltsbuchId;
        this.vermögen = vermögen;
    }
}
