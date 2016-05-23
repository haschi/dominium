package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.coding.aspekte.ValueObject;

import java.util.UUID;

@ValueObject
public class BeginneHaushaltsbuchführung {
    public final UUID identitätsmerkmal;

    public BeginneHaushaltsbuchführung(final UUID identitätsmerkmal) {
        super();

        this.identitätsmerkmal = identitätsmerkmal;
    }
}
