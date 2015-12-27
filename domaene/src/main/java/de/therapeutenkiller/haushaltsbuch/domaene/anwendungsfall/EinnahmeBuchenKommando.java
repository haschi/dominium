package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class EinnahmeBuchenKommando {
    public final UUID haushaltsbuch;
    public final String sollkonto;
    public final String habenkonto;
    public final MonetaryAmount währungsbetrag;

    public EinnahmeBuchenKommando(
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
