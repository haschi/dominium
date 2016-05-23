package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.google.common.collect.ImmutableCollection;

import javax.inject.Inject;
import java.util.UUID;

public class AlleHaushaltsbücher {

    private final HaushaltsbuchRepository repository;

    @Inject
    public AlleHaushaltsbücher(final HaushaltsbuchRepository repository) {
        super();
        this.repository = repository;
    }

    public final ImmutableCollection<UUID> abfragen() {
        return this.repository.alle();
    }
}
