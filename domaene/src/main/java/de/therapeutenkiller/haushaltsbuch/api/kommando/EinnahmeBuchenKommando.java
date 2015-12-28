package de.therapeutenkiller.haushaltsbuch.api.kommando;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class EinnahmeBuchenKommando {
    public final UUID haushaltsbuchId;
    public final String sollkonto;
    public final String habenkonto;
    public final MonetaryAmount währungsbetrag;

    public EinnahmeBuchenKommando(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount währungsbetrag) {

        this.haushaltsbuchId = haushaltsbuchId;
        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }
}
