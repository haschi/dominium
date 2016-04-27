package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.coding.annotation.Builder;
import de.therapeutenkiller.coding.aspekte.ValueObject;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Builder
@ValueObject
public class BucheAnfangsbestand {
    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final MonetaryAmount währungsbetrag;

    public BucheAnfangsbestand(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount währungsbetrag) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.währungsbetrag = währungsbetrag;
    }
}
