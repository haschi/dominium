package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.google.common.collect.ImmutableCollection;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.inject.Inject;
import java.util.UUID;

public final class AlleKonten {

    private final HaushaltsbuchRepository repository;

    @Inject
    public AlleKonten(final HaushaltsbuchRepository repository) {
        super();
        this.repository = repository;
    }

    public ImmutableCollection<Konto> abfragen(final UUID haushaltsbuchId) throws AggregatNichtGefunden {
        final Haushaltsbuch haushaltsbuch = this.repository.suchen(haushaltsbuchId);
        return haushaltsbuch.getKonten(); // NOPMD LoD TODO
    }
}
