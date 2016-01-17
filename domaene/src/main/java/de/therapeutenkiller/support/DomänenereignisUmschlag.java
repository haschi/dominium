package de.therapeutenkiller.support;

/**
 * Schnittstelle für Domänenereignis-Umschläge. Der DomänenereignisUmschlag  kapselt ein Domänenereignis und
 * fügt Meta-Informationen hinzu.
 *
 * @param <A> Der Typ der Aggregatwurzel, auf dass sich das gekapselte Domänenereignis bezieht.
 */
public interface DomänenereignisUmschlag<A> {
    Domänenereignis<A> getEreignis();

    int getVersion();

    String getStreamName();
}
