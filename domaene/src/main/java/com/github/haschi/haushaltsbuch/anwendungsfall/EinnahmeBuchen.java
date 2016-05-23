package com.github.haschi.haushaltsbuch.anwendungsfall;

import com.github.haschi.haushaltsbuch.api.kommando.FügeBuchungssatzHinzu;
import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.api.kommando.BucheEinnahme;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class EinnahmeBuchen {

    private Event<FügeBuchungssatzHinzu> fügeBuchungssatzHinzuEvent;

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
