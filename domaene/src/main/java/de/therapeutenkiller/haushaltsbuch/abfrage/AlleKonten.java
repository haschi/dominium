package de.therapeutenkiller.haushaltsbuch.abfrage;

import com.google.common.collect.ImmutableCollection;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

/**
 * Created by matthias on 23.12.15.
 */
@Singleton
public final class AlleKonten {
    private final HaushaltsbuchRepository repository;

    @Inject
    public AlleKonten(final HaushaltsbuchRepository repository) {

        this.repository = repository;
    }

    public ImmutableCollection<Konto> abfragen(final UUID haushaltsbuchId) {
        final Haushaltsbuch haushaltsbuch = this.repository.findBy(haushaltsbuchId);
        return haushaltsbuch.getKonten(); // NOPMD LoD TODO
    }
}
