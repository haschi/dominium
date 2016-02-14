package de.therapeutenkiller.dominium.persistenz.jpa;

public class Serialisierungsfehler extends RuntimeException {

    public Serialisierungsfehler(final Exception grund) {
        super(grund);
    }
}
