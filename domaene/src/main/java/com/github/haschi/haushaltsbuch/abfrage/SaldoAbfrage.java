package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Saldo;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;

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
