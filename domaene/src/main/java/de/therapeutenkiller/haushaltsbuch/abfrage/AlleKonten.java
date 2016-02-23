package de.therapeutenkiller.haushaltsbuch.abfrage;

import com.google.common.collect.ImmutableCollection;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;

import javax.inject.Inject;
import java.util.UUID;

public final class AlleKonten {

    private final HaushaltsbuchRepository repository;

    @Inject
    public AlleKonten(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public ImmutableCollection<Konto> abfragen(final UUID haushaltsbuchId) throws AggregatNichtGefunden {
        final Haushaltsbuch haushaltsbuch = this.repository.suchen(haushaltsbuchId);
        return haushaltsbuch.konten.getKonten(); // NOPMD LoD TODO
    }
}
