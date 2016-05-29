package com.github.haschi.haushaltsbuch.anwendungsfall;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.api.kommando.FügeBuchungssatzHinzu;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Buchungssatz;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class BuchungssatzHinzufügen {

    @Inject
    private HaushaltsbuchRepository repository;

    @Inject
    public BuchungssatzHinzufügen(final HaushaltsbuchRepository repository) {
        super();

        this.repository = repository;
    }

    public BuchungssatzHinzufügen() {
        super();
    }

    public void ausführen(@Observes final FügeBuchungssatzHinzu befehl)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        final Haushaltsbuch haushaltsbuch = this.repository.suchen(befehl.identitätsmerkmal);
        final Buchungssatz buchungssatz = new Buchungssatz(befehl.sollkonto, befehl.habenkonto, befehl.betrag);
        haushaltsbuch.buchungssatzHinzufügen(buchungssatz);

        this.repository.speichern(haushaltsbuch, haushaltsbuch.getAggregatverwalter());
    }
}
