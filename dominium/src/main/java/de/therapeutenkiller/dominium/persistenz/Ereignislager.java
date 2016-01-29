package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import de.therapeutenkiller.dominium.modell.Domänenereignis;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Dauerhafte Ablage für Domänenereignisse.
 *
 * @param <E> Der Schnappschuss-Typ der Aggregatwurzel, auf das sich die Domänenereignisse beziehen
 * @param <A> Der Typ der Aggregatwurzel, auf das sich die Domänenereignisse beziehen.
 */
public interface Ereignislager<E, A> {

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
}
