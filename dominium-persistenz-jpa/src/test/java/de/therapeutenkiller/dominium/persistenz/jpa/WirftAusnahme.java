package de.therapeutenkiller.dominium.persistenz.jpa;

@FunctionalInterface
public interface WirftAusnahme {
    void run() throws Throwable;
}
