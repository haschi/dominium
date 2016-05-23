package com.github.haschi.dominium.persistenz;

import com.github.haschi.dominium.modell.Aggregatwurzel;
import com.github.haschi.dominium.modell.Schnappschuss;

public interface Repository<A extends Aggregatwurzel<A, I, T, S>, I, T, S extends Schnappschuss<I>> {
    A suchen(I identitätsmerkmal) throws AggregatNichtGefunden;

    void hinzufügen(A aggregat);

    void speichern(A aggregat) throws KonkurrierenderZugriff, AggregatNichtGefunden;
}
