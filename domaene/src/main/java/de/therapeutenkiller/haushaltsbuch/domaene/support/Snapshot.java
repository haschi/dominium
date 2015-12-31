package de.therapeutenkiller.haushaltsbuch.domaene.support;

interface Snapshot<T> {
    T getIdentifier();

    int getVersion();
}
