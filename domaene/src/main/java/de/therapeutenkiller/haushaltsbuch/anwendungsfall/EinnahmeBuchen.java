package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BucheEinnahme;
import de.therapeutenkiller.haushaltsbuch.api.kommando.FügeBuchungssatzHinzu;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class EinnahmeBuchen {

    private Event<FügeBuchungssatzHinzu> fügeBuchungssatzHinzuEvent = null;

    @Inject
    public EinnahmeBuchen(final Event<FügeBuchungssatzHinzu> fügeBuchungssatzHinzuEvent) {
        super();

        this.fügeBuchungssatzHinzuEvent = fügeBuchungssatzHinzuEvent;
    }

    public EinnahmeBuchen() {
        super();
    }

    public void ausführen(@Observes final BucheEinnahme kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        this.fügeBuchungssatzHinzuEvent.fire(new FügeBuchungssatzHinzu(
                kommando.haushaltsbuchId,
                kommando.sollkonto,
                kommando.habenkonto,
                kommando.währungsbetrag));
    }
}
