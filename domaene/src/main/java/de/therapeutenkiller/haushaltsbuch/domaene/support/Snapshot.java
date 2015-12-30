package de.therapeutenkiller.haushaltsbuch.domaene.support;

/**
 * Created by matthias on 30.12.15.
 */
public interface Snapshot<T> {
    T getIdentifier();

    int getVersion();
}
