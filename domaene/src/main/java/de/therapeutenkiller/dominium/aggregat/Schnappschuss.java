package de.therapeutenkiller.dominium.aggregat;

public interface Schnappschuss<A extends Aggregatwurzel<A, T>, T> {
    T getIdentitätsmerkmal();

    long getVersion();
}
