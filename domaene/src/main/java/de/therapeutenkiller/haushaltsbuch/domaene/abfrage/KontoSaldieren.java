package de.therapeutenkiller.haushaltsbuch.domaene.abfrage;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class KontoSaldieren {

    private final HaushaltsbuchRepository repository;

    @Inject
    public KontoSaldieren(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public final Saldo ausführen(final UUID aktuellesHaushaltsbuch, final String kontoname) {
        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(aktuellesHaushaltsbuch);
        return haushaltsbuch.kontostandBerechnen(kontoname); // NOPMD LoD TODO
    }
}
