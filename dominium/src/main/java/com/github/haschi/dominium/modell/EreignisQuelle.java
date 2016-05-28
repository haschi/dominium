package com.github.haschi.dominium.modell;

import java.util.HashSet;
import java.util.Set;

/**
 * Quelle der Domänenereignisse
 *
 * Abonnenten können sich für den Empfang von Domänenereignissen registrieren.
 * Die Abonnenten werden synchron in beliebiger Reihenfolge benachrichtigt.
 * @param <T> Der Typ der Domänenereignisse
 */
public final class EreignisQuelle<T> {

    private final Set<EreignisZiel<T>> abonnenten = new HashSet<>();

    private final Set<T> tabonnenten = new HashSet<>();

    public EreignisQuelle() {
        super();
    }

    /**
     * Löst ein Ereignis aus.
     *
     * @param ereignis Ein Domänenereignis, dass an die Abonnenten weitergeleitet wird.
     */
    public void bewirkt(final Domänenereignis<T> ereignis) {
        this.abonnenten.forEach(ereignis::anwendenAuf);
        this.tabonnenten.forEach(ereignis::anwendenAuf);
    }

    /**
     * Registriert einen Abonnenten für die Ereignisse dieser
     * Quelle
     *
     * @param abonnent Ein Abonnent, der die Schnittstelle {@link EreignisZiel} entgegen nimmt.
     */
    public void abonnieren(final EreignisZiel<T> abonnent) {
        this.abonnenten.add(abonnent);
    }

    /**
     * Registriert einen Abonnenten für die Ereignisse dieser
     * Quelle.
     *
     * @param abonnent Ein Abonnent, der Ereignisse des Typs T entgegen nimmt
     */
    public void abonnieren(final T abonnent) {
        this.tabonnenten.add(abonnent);
    }
}
