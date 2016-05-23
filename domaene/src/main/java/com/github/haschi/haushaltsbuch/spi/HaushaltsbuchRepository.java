package com.github.haschi.haushaltsbuch.spi;

import com.google.common.collect.ImmutableCollection;
import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import java.util.UUID;

public interface HaushaltsbuchRepository {

    Haushaltsbuch suchen(UUID identitätsmerkmal) throws AggregatNichtGefunden;

    void hinzufügen(Haushaltsbuch haushaltsbuch);

    void speichern(Haushaltsbuch haushaltsbuch) throws KonkurrierenderZugriff, AggregatNichtGefunden;

    ImmutableCollection<UUID> alle();
}
