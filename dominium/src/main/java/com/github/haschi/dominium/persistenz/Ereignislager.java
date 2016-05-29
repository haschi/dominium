package com.github.haschi.dominium.persistenz;

import com.github.haschi.dominium.modell.Domänenereignis;
import com.github.haschi.dominium.modell.Versionsbereich;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Dauerhafte Ablage für Domänenereignisse.
 *
 */
public interface Ereignislager<I, T> {

    void neuenEreignisstromErzeugen(
            I identitätsmerkmal,
            Collection<Domänenereignis<T>> domänenereignisse);

    void ereignisseDemStromHinzufügen(
            I identitätsmerkmal,
            long erwarteteVersion, Collection<Domänenereignis<T>> domänenereignisse)
        throws KonkurrierenderZugriff, EreignisstromWurdeNichtGefunden;

    List<Domänenereignis<T>> getEreignisliste(I identitätsmerkmal, Versionsbereich bereich);

    /**
     * @return Liefert die Identitätsmerkmale aller Ereignisströme des Lagers.
     * TODO Löschen, wird nur für Tests verwendet.
     */
    Stream<I> getEreignisströme();

    boolean existiertEreignisStrom(I identitätsmerkmal);
}
