package com.github.haschi.haushaltsbuch.anwendungsfall;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.api.kommando.BucheTilgung;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.github.haschi.dominium.infrastructure.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class TilgungBuchen {

    @Inject
    HaushaltsbuchRepository repository;

    public void ausführen(@Observes final BucheTilgung bucheTilgung)
            throws AggregatNichtGefunden, KonkurrierenderZugriff {
        final Haushaltsbuch haushaltsbuch = this.repository.suchen(bucheTilgung.haushaltsbuchId);
        haushaltsbuch.tilgungBuchen(bucheTilgung.sollkonto, bucheTilgung.habenkonto, bucheTilgung.währungsbetrag);
        this.repository.speichern(haushaltsbuch.getIdentitätsmerkmal(), haushaltsbuch.getAggregatverwalter());
    }
}
