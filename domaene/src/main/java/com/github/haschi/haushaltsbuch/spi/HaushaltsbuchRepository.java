package com.github.haschi.haushaltsbuch.spi;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.dominium.infrastructure.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.google.common.collect.ImmutableCollection;

import java.util.UUID;

public interface HaushaltsbuchRepository {

    Haushaltsbuch suchen(UUID identitätsmerkmal) throws AggregatNichtGefunden;

    void hinzufügen(
        UUID identitätsmerkmal,
        Aggregatverwalter<HaushaltsbuchEreignisziel> ziel);

    void speichern(
        UUID identitätsmerkmal,
        Aggregatverwalter<HaushaltsbuchEreignisziel> ziel)
        throws KonkurrierenderZugriff, AggregatNichtGefunden;

    ImmutableCollection<UUID> alle();
}
