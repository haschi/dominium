package de.therapeutenkiller.haushaltsbuch.spi;

import com.google.common.collect.ImmutableCollection;
import de.therapeutenkiller.dominium.persistenz.EreignisstromNichtVorhanden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import java.util.UUID;

public interface HaushaltsbuchRepository {

    Haushaltsbuch findBy(UUID identitätsmerkmal) throws EreignisstromNichtVorhanden;

    void add(Haushaltsbuch haushaltsbuch) throws KonkurrierenderZugriff;

    void save(Haushaltsbuch haushaltsbuch) throws KonkurrierenderZugriff;

    ImmutableCollection<UUID> alle();
}
