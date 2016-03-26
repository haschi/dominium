package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.FügeBuchungssatzHinzu;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Stateless
public final class BuchungssatzHinzufügen {

    @Inject
    private HaushaltsbuchRepository repository;

    @Inject
    public BuchungssatzHinzufügen(final HaushaltsbuchRepository repository) {

        this.repository = repository;
    }

    public BuchungssatzHinzufügen() {
    }

    public void ausführen(@Observes final FügeBuchungssatzHinzu befehl)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        final Haushaltsbuch haushaltsbuch = this.repository.suchen(befehl.identitätsmerkmal);

        haushaltsbuch.buchungssatzHinzufügen(
                befehl.sollkonto,
                befehl.habenkonto,
                befehl.betrag);

        this.repository.speichern(haushaltsbuch);
    }
}
