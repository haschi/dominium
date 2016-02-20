package de.therapeutenkiller.haushaltsbuch.abfrage;

import com.google.common.collect.ImmutableCollection;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.inject.Inject;
import java.util.UUID;

public class AlleHaushaltsbücher {

    private final HaushaltsbuchRepository repository;

    @Inject
    public AlleHaushaltsbücher(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public final ImmutableCollection<UUID> abfragen() {
        return this.repository.alle();
    }
}
