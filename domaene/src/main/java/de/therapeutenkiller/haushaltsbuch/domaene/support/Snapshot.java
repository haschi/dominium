package de.therapeutenkiller.haushaltsbuch.domaene.support;

public interface Snapshot<T> {
    T getIdentifier();

    int getVersion();
}
