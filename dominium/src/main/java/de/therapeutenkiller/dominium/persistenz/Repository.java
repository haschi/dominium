package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

public interface Repository<A extends Aggregatwurzel<A, I, T, S>, I, T, S extends Schnappschuss<A, I>> {
    A suchen(I identitätsmerkmal) throws AggregatNichtGefunden;

    void hinzufügen(A aggregat);

    void speichern(A aggregat) throws KonkurrierenderZugriff, AggregatNichtGefunden;
}
