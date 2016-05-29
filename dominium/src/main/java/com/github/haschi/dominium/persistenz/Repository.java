package com.github.haschi.dominium.persistenz;

import com.github.haschi.dominium.modell.Aggregatverwalter;
import com.github.haschi.dominium.modell.Aggregatwurzel;
import com.github.haschi.dominium.modell.Schnappschuss;

public interface Repository<A extends Aggregatwurzel<A, I, T, S>, I, T, S extends Schnappschuss> {
    A suchen(I identitätsmerkmal) throws AggregatNichtGefunden;

    void hinzufügen(final I identitätsmerkmal, final Aggregatverwalter<T> ziel);

    void speichern(A aggregat, final Aggregatverwalter<T> ziel) throws KonkurrierenderZugriff, AggregatNichtGefunden;
}
