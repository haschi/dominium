package de.therapeutenkiller.dominium.modell;

import java.io.Serializable;

/**
 * Schnittstelle für Domänenereignisse
 *
 * Domänenereignisse enthalten die Informationen der Zustandsänderungen
 * für ein Aggregat.
 *
 * @param <A> Der Typ des Aggregats, zu dem das Ereignis gehört.
 */
public interface Domänenereignis<A> extends Serializable {

    /**
     * Wendet eine durch das Domänenereignis beschriebene Zustandsänderung auf
     * ein Aggregat an. Die Implementierung der Methode sollte das
     * Domänenereignis an ein Exemplar des Aggregats weiterleiten. Das Aggregat
     * soll seinen Zustand dem Domänenereignis entsprechend aktualisieren.
     *
     * {@code
     * void anwendenAuf(BeispielAggregat aggregat) {
     *     aggregat.falls(this);
     * }
     * }
     *
     * @param aggregat Ein Aggregat.
     */
    void anwendenAuf(A aggregat);
}
