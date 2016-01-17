package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class HaushaltsbuchführungBeginnen {
    private final HaushaltsbuchRepository repository;

    @Inject
    public HaushaltsbuchführungBeginnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public final void ausführen(@Observes final HaushaltsbuchführungBeginnenKommando kommando) {
        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch(UUID.randomUUID());
        this.getRepository().add(haushaltsbuch);
    }

    private HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
