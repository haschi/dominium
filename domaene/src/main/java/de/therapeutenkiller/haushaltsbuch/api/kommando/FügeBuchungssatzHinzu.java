package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.dominium.modell.Wertobjekt;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class FügeBuchungssatzHinzu extends Wertobjekt {

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
