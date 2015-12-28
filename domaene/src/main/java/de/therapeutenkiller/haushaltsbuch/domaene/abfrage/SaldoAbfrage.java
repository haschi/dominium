package de.therapeutenkiller.haushaltsbuch.domaene.abfrage;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class SaldoAbfrage {

    private final HaushaltsbuchRepository repository;

    @Inject
    public SaldoAbfrage(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public final Saldo abfragen(final UUID haushaltsbuchId, final String kontoname) {
        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);
        return haushaltsbuch.kontostandBerechnen(kontoname); // NOPMD LoD TODO
    }
}
