package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.coding.annotation.Builder;
import de.therapeutenkiller.coding.aspekte.ValueObject;

import java.util.UUID;

@Builder
@ValueObject
public class BeginneHaushaltsbuchführung {
    public final UUID identitätsmerkmal;

    public BeginneHaushaltsbuchführung(final UUID identitätsmerkmal) {
        super();

        this.identitätsmerkmal = identitätsmerkmal;
    }
}
