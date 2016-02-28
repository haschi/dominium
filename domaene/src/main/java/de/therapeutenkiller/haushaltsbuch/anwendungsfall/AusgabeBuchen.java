package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BucheAusgabe;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class AusgabeBuchen {

    @Inject
    private HaushaltsbuchRepository repository;

    public void ausführen(
            @Observes(during = TransactionPhase.BEFORE_COMPLETION)
            final BucheAusgabe kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {
        final Haushaltsbuch haushaltsbuch = this.repository.suchen(kommando.haushaltsbuchId);

        haushaltsbuch.ausgabeBuchen(kommando.sollkonto, kommando.habenkonto, kommando.währungsbetrag);
        this.repository.speichern(haushaltsbuch);
    }
}
