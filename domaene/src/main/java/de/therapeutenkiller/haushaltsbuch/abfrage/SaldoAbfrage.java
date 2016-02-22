package de.therapeutenkiller.haushaltsbuch.abfrage;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;

import javax.inject.Inject;
import java.util.UUID;

public class SaldoAbfrage {

    @Inject
    private HaushaltsbuchRepository repository;

    public final Saldo abfragen(final UUID haushaltsbuchId, final String kontoname) throws AggregatNichtGefunden {
        final Haushaltsbuch haushaltsbuch = this.repository.suchen(haushaltsbuchId);
        return haushaltsbuch.kontostandBerechnen(kontoname);
    }
}
