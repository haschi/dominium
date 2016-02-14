package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.EreignisstromNichtVorhanden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.EinnahmeBuchenKommando;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Singleton
public final class EinnahmeBuchen {

    private final BuchungssatzHinzufügen buchungssatzHinzufügen;

    @Inject
    public EinnahmeBuchen(final BuchungssatzHinzufügen buchungssatzHinzufügen) {

        this.buchungssatzHinzufügen = buchungssatzHinzufügen;
    }

    public void ausführen(@Observes final EinnahmeBuchenKommando kommando)
            throws KonkurrierenderZugriff, EreignisstromNichtVorhanden {

        // TODO ggf. später prüfen, ob die Kontenarten korrekt sind.
        this.buchungssatzHinzufügen.ausführen(
                kommando.haushaltsbuchId,
                kommando.sollkonto,
                kommando.habenkonto,
                kommando.währungsbetrag);
    }
}
