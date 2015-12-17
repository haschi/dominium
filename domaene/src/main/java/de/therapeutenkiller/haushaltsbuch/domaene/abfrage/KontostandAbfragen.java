package de.therapeutenkiller.haushaltsbuch.domaene.abfrage;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public class KontostandAbfragen {
    private final HaushaltsbuchRepository repository;

    @Inject
    public KontostandAbfragen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public final MonetaryAmount ausführen(final String kontoname, final UUID haushaltsbuchId) {
        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);
        return haushaltsbuch.kontostandBerechnen(kontoname);
    }
}
