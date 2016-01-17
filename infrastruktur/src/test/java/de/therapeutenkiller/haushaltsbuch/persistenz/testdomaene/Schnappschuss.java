package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

public interface Schnappschuss<A extends Aggregatwurzel<A, T>, T> {
    T getIdentitätsmerkmal();

    long getVersion();
}
