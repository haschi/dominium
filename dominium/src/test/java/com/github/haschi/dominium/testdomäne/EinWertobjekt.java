package com.github.haschi.dominium.testdomäne;

import com.github.haschi.coding.aspekte.ValueObject;

@ValueObject
public final class EinWertobjekt {
    public final String vorname;
    public final String nachname;

    public EinWertobjekt(final String vorname, final String nachname) {
        super();

        this.vorname = vorname;
        this.nachname = nachname;
    }
}
