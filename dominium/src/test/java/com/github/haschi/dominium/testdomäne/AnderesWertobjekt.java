package com.github.haschi.dominium.testdomäne;

import com.github.haschi.coding.aspekte.ValueObject;

@ValueObject(exclude = "straße")
public final class AnderesWertobjekt {
    private final String straße;
    private final String postleitzahl;
    private final String ort;

    public AnderesWertobjekt(final String straße, final String postleitzahl, final String ort) {
        super();

        this.straße = straße;
        this.postleitzahl = postleitzahl;
        this.ort = ort;
    }
}
