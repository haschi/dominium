package de.therapeutenkiller.haushaltsbuch.domaene;

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
