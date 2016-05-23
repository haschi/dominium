package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.coding.aspekte.ValueObject;

import javax.money.MonetaryAmount;
import java.util.UUID;

@ValueObject
public class BucheAusgabe {
    public final UUID haushaltsbuchId;
    public final String sollkonto;
    public final String habenkonto;
    public final MonetaryAmount währungsbetrag;

    public BucheAusgabe(
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
