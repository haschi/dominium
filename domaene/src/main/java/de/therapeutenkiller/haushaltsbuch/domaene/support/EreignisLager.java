package de.therapeutenkiller.haushaltsbuch.domaene.support;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

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

    void neuenEreignisstromErzeugen(String streamName, Collection<Domänenereignis<A>> domänenereignisse);

    void ereignisseDemStromHinzufügen(
            String streamName,
            Collection<Domänenereignis<A>> domänenereignisse,
            Optional<Integer> erwarteteVersion);

    List<Domänenereignis<A>> getEreignisListe(String streamName, int vonVersion, int bisVersion);

    void schnappschussHinzufügen(String streamName, E snapshot);

    @DarfNullSein
    E getNeuesterSchnappschuss(String streamName);

    /**
     * Liefert das Ereignis, mit dem ein Aggregat erzeugt worden ist.
     * @param streamName Name des Streams für das Aggregat
     * @return Das Initialereignis.
     */
    Domänenereignis<A> getInitialereignis(String streamName);
}
