package de.therapeutenkiller.haushaltsbuch.domaene.api;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class AnfangsbestandBuchenKommando {
    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final MonetaryAmount währungsbetrag;

    public AnfangsbestandBuchenKommando(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount währungsbetrag) {

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.währungsbetrag = währungsbetrag;
    }
}
