package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.coding.aspekte.ValueObject;

import javax.money.MonetaryAmount;
import java.util.UUID;

@ValueObject
public class FügeBuchungssatzHinzu {

    public final UUID identitätsmerkmal;
    public final String sollkonto;
    public final String habenkonto;
    public final MonetaryAmount betrag;

    public FügeBuchungssatzHinzu(
            final UUID identitätsmerkmal,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {
        super();
        this.identitätsmerkmal = identitätsmerkmal;
        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.betrag = betrag;
    }
}
