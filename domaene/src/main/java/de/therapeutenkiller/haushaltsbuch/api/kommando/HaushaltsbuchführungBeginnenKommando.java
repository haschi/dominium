package de.therapeutenkiller.haushaltsbuch.api.kommando;

import java.util.UUID;

public class HaushaltsbuchführungBeginnenKommando {
    public final UUID identitätsmerkmal;

    public HaushaltsbuchführungBeginnenKommando(final UUID identitätsmerkmal) {

        this.identitätsmerkmal = identitätsmerkmal;
    }
}
