package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;

public interface Repository<A extends Aggregatwurzel<A, E, I, T>, E extends Domänenereignis<T>, I, T> {
    A suchen(I identitätsmerkmal) throws AggregatNichtGefunden;

    void hinzufügen(A aggregat);

    void speichern(A aggregat) throws KonkurrierenderZugriff;
}
