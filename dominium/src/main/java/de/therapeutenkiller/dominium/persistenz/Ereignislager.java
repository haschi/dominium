package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Dauerhafte Ablage für Domänenereignisse.
 *
 * @param <A> Der Typ der Aggregatwurzel, auf das sich die Domänenereignisse beziehen.
 */
public interface Ereignislager<A extends Aggregatwurzel<A, I>, I> {

    void neuenEreignisstromErzeugen(
            String streamName,
            Collection<Domänenereignis<A>> domänenereignisse);

    void ereignisseDemStromHinzufügen(
            String streamName,
            Collection<Domänenereignis<A>> domänenereignisse,
            Optional<Long> erwarteteVersion);

    List<Domänenereignis<A>> getEreignisListe(String streamName, long vonVersion, long bisVersion);

    void schnappschussHinzufügen(String streamName, Schnappschuss<A, I> snapshot);

    @DarfNullSein
    Optional<Schnappschuss<A, I>> getNeuesterSchnappschuss(String streamName);
}
