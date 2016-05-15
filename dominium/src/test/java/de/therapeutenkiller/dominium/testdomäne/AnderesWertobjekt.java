package de.therapeutenkiller.dominium.testdomäne;

import de.therapeutenkiller.coding.annotation.Builder;
import de.therapeutenkiller.coding.aspekte.ValueObject;

@ValueObject(exclude = {"straße"})
@Builder
public final class AnderesWertobjekt {
    public final String straße;
    public final String postleitzahl;
    public final String ort;

    public AnderesWertobjekt(final String straße, final String postleitzahl, final String ort) {
        this.straße = straße;
        this.postleitzahl = postleitzahl;
        this.ort = ort;
    }
}
