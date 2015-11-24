package de.therapeutenkiller.haushaltsbuch.domaene.support;

@FunctionalInterface
public interface Ereignishandler<T> {
    void ausführen(T ereignis);
}
