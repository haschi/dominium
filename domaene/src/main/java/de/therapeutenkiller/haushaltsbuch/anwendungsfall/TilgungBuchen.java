package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BucheTilgung;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

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
        this.repository.speichern(haushaltsbuch);
    }
}
