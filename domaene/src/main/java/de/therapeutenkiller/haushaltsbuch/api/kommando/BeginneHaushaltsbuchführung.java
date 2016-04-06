package de.therapeutenkiller.haushaltsbuch.api.kommando;

import java.util.UUID;

public class BeginneHaushaltsbuchführung {
    public final UUID identitätsmerkmal;

    public BeginneHaushaltsbuchführung(final UUID identitätsmerkmal) {
        super();

        this.identitätsmerkmal = identitätsmerkmal;
    }
}
