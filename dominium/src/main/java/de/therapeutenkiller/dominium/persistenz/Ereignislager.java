package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Dauerhafte Ablage für Domänenereignisse.
 *
 * @param <A> Der Typ der Aggregatwurzel, auf das sich die Domänenereignisse beziehen.
 */
public interface Ereignislager<A extends Aggregatwurzel<A, I, T>, I, T> {

    void neuenEreignisstromErzeugen(
            I identitätsmerkmal,
            Collection<Domänenereignis<T>> domänenereignisse);

    void ereignisseDemStromHinzufügen(
            I identitätsmerkmal,
            long erwarteteVersion, Collection<Domänenereignis<T>> domänenereignisse) throws KonkurrierenderZugriff;

    List<Domänenereignis<T>> getEreignisliste(I identitätsmerkmal, Versionsbereich bereich);

    void schnappschussHinzufügen(I identitätsmerkmal, Schnappschuss<A, I, T> snapshot) throws AggregatNichtGefunden;

    Optional<Schnappschuss<A, I, T>> getNeuesterSchnappschuss(I identitätsmerkmal) throws AggregatNichtGefunden;

    /**
     * @return Liefert die Identitätsmerkmale aller Ereignisströme des Lagers.
     */
    Stream<I> getEreignisströme();
}
