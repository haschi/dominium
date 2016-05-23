package com.github.haschi.dominium.modell;

import java.io.Serializable;

/**
 * Der Schnappschuss eines Aggregats.
 *
 * Vom Aggregat werden Schnappschüsse gespeichert, damit nicht immer alle
 * Ereignisse des Aggregats geladen und ausgewertet werden müssen.
 *
 * Jedes Aggregat muss einen Implementierung seines Schnappschusses bereitstellen.
 *
 * @param <I> Der Typ des Identitätsmerkmals des Aggregats zu dem der Schnappschuss gehört.
 */
public interface Schnappschuss<I> extends Serializable {

    I getIdentitätsmerkmal();

    Version getVersion();
}
