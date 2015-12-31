package de.therapeutenkiller.haushaltsbuch.domaene.support;

public interface Domänenereignis<T extends AggregateRoot<?, ?>> {
    void applyTo(T aggregat);
}
