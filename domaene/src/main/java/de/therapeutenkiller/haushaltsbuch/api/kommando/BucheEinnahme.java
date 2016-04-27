package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.coding.annotation.Builder;
import de.therapeutenkiller.coding.aspekte.ValueObject;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Builder
@ValueObject
public class BucheEinnahme {
    public final UUID haushaltsbuchId;
    public final String sollkonto;
    public final String habenkonto;
    public final MonetaryAmount währungsbetrag;

    public BucheEinnahme(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount währungsbetrag) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }
}
