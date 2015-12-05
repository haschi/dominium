package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import javax.money.MonetaryAmount;
import java.util.UUID;

public final class VermögenWurdeGeändert {

    private final UUID haushaltsbuchId;

    private final MonetaryAmount vermögen;

    public UUID getHaushaltsbuchId() {
        return this.haushaltsbuchId;
    }

    public MonetaryAmount getVermögen() {
        return this.vermögen;
    }

    public VermögenWurdeGeändert(final UUID haushaltsbuchId, final MonetaryAmount vermögen) {

        this.haushaltsbuchId = haushaltsbuchId;
        this.vermögen = vermögen;
    }
}
