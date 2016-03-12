package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;

/**
 * Created by matthias on 12.03.16.
 */
public interface Repository<A extends Aggregatwurzel<A, I, T>, I, T> {
    A suchen(I identitätsmerkmal) throws AggregatNichtGefunden;

    void hinzufügen(A aggregat);

    void speichern(A aggregat) throws KonkurrierenderZugriff;
}
