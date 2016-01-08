package de.therapeutenkiller.haushaltsbuch.domaene.support;

/**
 *
 * @param <T> Der Typ des Aggregats, zu dem das Ereignis gehört.
 */
public interface Domänenereignis<T> {
    void applyTo(T aggregat);
}
