package de.therapeutenkiller.dominium.lagerung;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.aggregat.Initialereignis;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Dauerhafte Ablage für Domänenereignisse.
 *
 * @param <E> Der Schnappschuss-Typ der Aggregatwurzel, auf das sich die Domänenereignisse beziehen
 * @param <A> Der Typ der Aggregatwurzel, auf das sich die Domänenereignisse beziehen.
 */
public interface EreignisLager<E, A> {

    void neuenEreignisstromErzeugen(
            String streamName,
            Collection<Domänenereignis<A>> domänenereignisse);

    void ereignisseDemStromHinzufügen(
            String streamName,
            Collection<Domänenereignis<A>> domänenereignisse,
            Optional<Long> erwarteteVersion);

    List<Domänenereignis<A>> getEreignisListe(String streamName, long vonVersion, long bisVersion);

    void schnappschussHinzufügen(String streamName, E snapshot);

    @DarfNullSein
    E getNeuesterSchnappschuss(String streamName);

    /**
     * Liefert das Ereignis, mit dem ein Aggregat erzeugt worden ist.
     * @param streamName Name des Streams für das Aggregat
     * @return Das Initialereignis.
     */
    <T> Initialereignis<A, T> getInitialereignis(String streamName);
}
