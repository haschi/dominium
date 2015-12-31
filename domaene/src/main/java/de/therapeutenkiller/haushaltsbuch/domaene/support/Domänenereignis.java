package de.therapeutenkiller.haushaltsbuch.domaene.support;

public interface Domänenereignis<T> {
    void applyTo(T aggregat);
}
