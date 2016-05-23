package com.github.haschi.dominium.testdomäne;

import com.github.haschi.coding.aspekte.ValueObject;

@ValueObject(exclude = "straße")
public final class AnderesWertobjekt {
    public final String straße;
    public final String postleitzahl;
    public final String ort;

    public AnderesWertobjekt(final String straße, final String postleitzahl, final String ort) {
        super();

        this.straße = straße;
        this.postleitzahl = postleitzahl;
        this.ort = ort;
    }
}
