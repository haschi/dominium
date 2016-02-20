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
public interface Ereignislager<A extends Aggregatwurzel<A, I>, I> {

    void neuenEreignisstromErzeugen(
            I identitätsmerkmal,
            Collection<Domänenereignis<A>> domänenereignisse) throws KonkurrierenderZugriff;

    void ereignisseDemStromHinzufügen(
            I identitätsmerkmal,
            long erwarteteVersion, Collection<Domänenereignis<A>> domänenereignisse) throws KonkurrierenderZugriff;

    List<Domänenereignis<A>> getEreignisListe(I identitätsmerkmal, Versionsbereich bereich);

    void schnappschussHinzufügen(I identitätsmerkmal, Schnappschuss<A, I> snapshot) throws EreignisstromNichtVorhanden;

    Optional<Schnappschuss<A, I>> getNeuesterSchnappschuss(I identitätsmerkmal) throws EreignisstromNichtVorhanden;

    Stream<I> getEreignisströme();
}
