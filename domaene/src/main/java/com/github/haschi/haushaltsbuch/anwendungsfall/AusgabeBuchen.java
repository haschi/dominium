package com.github.haschi.haushaltsbuch.anwendungsfall;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.api.kommando.BucheAusgabe;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.github.haschi.dominium.infrastructure.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class AusgabeBuchen {

    @Inject
    private HaushaltsbuchRepository repository;

    public void ausführen(
            @Observes
            final BucheAusgabe kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {
        final Haushaltsbuch haushaltsbuch = this.repository.suchen(kommando.haushaltsbuchId);

        haushaltsbuch.ausgabeBuchen(kommando.sollkonto, kommando.habenkonto, kommando.währungsbetrag);
        this.repository.speichern(haushaltsbuch.getIdentitätsmerkmal(), haushaltsbuch.getAggregatverwalter());
    }
}
