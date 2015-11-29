package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;

/**
 * Testmittel für die Domänenereignis Funktionalität.
 */
public class IchTueNichts extends Domänenereignis {
    private final Integer zahl;

    public IchTueNichts(final Integer zahl) {
        super();
        this.zahl = zahl;
    }

    public final Integer getZahl() {
        return this.zahl;
    }
}
