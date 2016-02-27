package de.therapeutenkiller.dominium.modell;

import java.io.Serializable;

/**
 * Der Schnappschuss eines Aggregats.
 *
 * Vom Aggregat werden Schnappschüsse gespeichert, damit nicht immer alle
 * Ereignisse des Aggregats geladen und ausgewertet werden müssen.
 *
 * Jedes Aggregat muss einen Implementierung seines Schnappschusses bereitstellen.
 *
 * @param <A> Der Typ des Aggregats, zu dem der Schnappschuss gehört.
 * @param <I> Der Typ des Identitätsmerkmals des Aggregats zu dem der Schnappschuss gehört.
 * @param <T> Die Schnittstelle des Ereignis-Ziels.
 */
public interface Schnappschuss<A extends Aggregatwurzel<A, I, T>, I, T> extends Serializable {
    I getIdentitätsmerkmal();

    long getVersion();

    A wiederherstellen();
}
