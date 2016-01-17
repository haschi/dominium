package de.therapeutenkiller.support;

/**
 * Schnittstelle für Domänenereignisse
 *
 * Domänenereignisse enthalten die Informationen der Zustandsänderungen
 * für ein Aggregat.
 *
 * @param <A> Der Typ des Aggregats, zu dem das Ereignis gehört.
 */
public interface Domänenereignis<A> {

    /**
     * Wendet eine durch das Domänenereignis beschriebene Zustandsänderung auf
     * ein Aggregat an.
     * @param aggregat Ein Aggregat.
     */
    void anwendenAuf(A aggregat);
}
