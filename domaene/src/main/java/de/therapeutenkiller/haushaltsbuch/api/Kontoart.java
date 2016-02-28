package de.therapeutenkiller.haushaltsbuch.api;

public enum Kontoart {
    Aktiv("Vermögen"),
    Passiv("Schulden"),
    Ertrag("Einnahmen"),
    Aufwand("Ausgaben");

    private final String bezeichnung;

    private Kontoart(final String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }
}
