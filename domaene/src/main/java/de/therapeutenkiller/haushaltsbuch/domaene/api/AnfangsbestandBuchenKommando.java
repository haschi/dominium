package de.therapeutenkiller.haushaltsbuch.domaene.api;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class AnfangsbestandBuchenKommando {
    public final UUID haushaltsbuch;
    public final String kontoname;
    public final MonetaryAmount währungsbetrag;

    public AnfangsbestandBuchenKommando(
            final UUID haushaltsbuch,
            final String kontoname,
            final MonetaryAmount währungsbetrag) {

        this.haushaltsbuch = haushaltsbuch;
        this.kontoname = kontoname;
        this.währungsbetrag = währungsbetrag;
    }
}
