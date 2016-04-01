package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Dauerhafte Ablage für Domänenereignisse.
 *
 */
public interface Ereignislager<E extends Domänenereignis<T>, I, T> {

    void neuenEreignisstromErzeugen(
            I identitätsmerkmal,
            Collection<E> domänenereignisse);

    void ereignisseDemStromHinzufügen(
            I identitätsmerkmal,
            long erwarteteVersion, Collection<E> domänenereignisse) throws KonkurrierenderZugriff;

    List<E> getEreignisliste(I identitätsmerkmal, Versionsbereich bereich);

    /**
     * @return Liefert die Identitätsmerkmale aller Ereignisströme des Lagers.
     */
    Stream<I> getEreignisströme();

    boolean existiertEreignisStrom(I identitätsmerkmal);
}
