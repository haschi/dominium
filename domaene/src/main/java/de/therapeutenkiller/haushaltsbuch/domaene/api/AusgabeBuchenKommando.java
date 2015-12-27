package de.therapeutenkiller.haushaltsbuch.domaene.api;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class AusgabeBuchenKommando {
    public final UUID haushaltsbuch;
    public final String sollkonto;
    public final String habenkonto;
    public final MonetaryAmount währungsbetrag;

    public AusgabeBuchenKommando(
            final UUID haushaltsbuch,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount währungsbetrag) {

        this.haushaltsbuch = haushaltsbuch;
        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }
}
