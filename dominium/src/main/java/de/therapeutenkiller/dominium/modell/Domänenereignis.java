package de.therapeutenkiller.dominium.modell;

import java.io.Serializable;

/**
 * Schnittstelle für Domänenereignisse
 *
 * Domänenereignisse enthalten die Informationen der Zustandsänderungen
 * für ein Aggregat.
 *
 * @param <A> Der Typ des Ereignis-Ziels, auf das ein Ereignis angewendet wird.
 */
public abstract class Domänenereignis<A> implements Serializable {

    /**
     * Wendet eine durch das Domänenereignis beschriebene Zustandsänderung auf
     * ein Aggregat an. Die Implementierung der Methode sollte das
     * Domänenereignis an ein Exemplar des Aggregats weiterleiten. Das Aggregat
     * soll seinen Zustand dem Domänenereignis entsprechend aktualisieren.
     *
     * {@code
     * void anwendenAuf(EreignisZiel ereignisZiel) {
     *     ereignisZiel.falls(this);
     * }
     * }
     *
     * @param ereignisZiel Das Ziel-Objekt, auf das ein Ereignis angewendet wird.
     */
    public abstract void anwendenAuf(A ereignisZiel);
}
