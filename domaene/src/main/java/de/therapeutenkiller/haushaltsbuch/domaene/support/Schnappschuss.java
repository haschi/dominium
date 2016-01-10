package de.therapeutenkiller.haushaltsbuch.domaene.support;

public interface Schnappschuss<T> {
    T getIdentitätsmerkmal();

    int getVersion();
}
