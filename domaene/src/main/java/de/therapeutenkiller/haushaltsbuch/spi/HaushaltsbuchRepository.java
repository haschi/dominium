package de.therapeutenkiller.haushaltsbuch.spi;

import com.google.common.collect.ImmutableCollection;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import java.util.UUID;

public interface HaushaltsbuchRepository {

    Haushaltsbuch suchen(UUID identitätsmerkmal) throws AggregatNichtGefunden;

    void hinzufügen(Haushaltsbuch haushaltsbuch) throws KonkurrierenderZugriff;

    void speichern(Haushaltsbuch haushaltsbuch) throws KonkurrierenderZugriff;

    ImmutableCollection<UUID> alle();
}
