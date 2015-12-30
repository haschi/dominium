package de.therapeutenkiller.haushaltsbuch.domaene.support;

/**
 * Created by matthias on 30.12.15.
 */
public interface Domänenereignis<T extends AggregateRoot<?, ?>> {
    void applyTo(T spiel);
}
