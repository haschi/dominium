package de.therapeutenkiller.dominium.testdomäne;

import de.therapeutenkiller.coding.aspekte.ValueObject;

@ValueObject
public final class EinWertobjekt {
    public final String vorname;
    public final String nachname;

    public EinWertobjekt(final String vorname, final String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }
}
