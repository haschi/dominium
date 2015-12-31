package de.therapeutenkiller.haushaltsbuch.spi;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import java.util.UUID;

public interface HaushaltsbuchRepository {

    void leeren();

    Haushaltsbuch findBy(UUID id);

    void add(Haushaltsbuch haushaltsbuch);

    void save(Haushaltsbuch haushaltsbuch);
}
