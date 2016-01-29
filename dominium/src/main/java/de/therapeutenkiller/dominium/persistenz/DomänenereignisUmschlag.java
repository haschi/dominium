package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

/**
 * Schnittstelle für Domänenereignis-Umschläge. Der DomänenereignisUmschlag  kapselt ein Domänenereignis und
 * fügt Meta-Informationen hinzu.
 *
 * @param <A> Der Typ der Aggregatwurzel, auf dass sich das gekapselte Domänenereignis bezieht.
 */
// TODO Primär-Schlüssel muss festgelegt werden
public interface DomänenereignisUmschlag<A> {
    Domänenereignis<A> getEreignis();

    long getVersion();

    String getStreamName();
}
