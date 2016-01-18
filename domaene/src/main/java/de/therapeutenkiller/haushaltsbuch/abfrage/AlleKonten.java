package de.therapeutenkiller.haushaltsbuch.abfrage;

import com.google.common.collect.ImmutableCollection;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class AlleKonten {

    @Inject
    private HaushaltsbuchRepository repository;

    public ImmutableCollection<Konto> abfragen(final UUID haushaltsbuchId) {
        final Haushaltsbuch haushaltsbuch = this.repository.findBy(haushaltsbuchId);
        return haushaltsbuch.getKonten(); // NOPMD LoD TODO
    }
}
