package de.therapeutenkiller.haushaltsbuch.domaene;

@FunctionalInterface
public interface Ereignishandler<T> {
    void ausführen(T ereignis);
}
