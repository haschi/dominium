package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.api.kommando.EinnahmeBuchenKommando;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class EinnahmeBuchen {

    private final BuchungssatzHinzufügen buchungssatzHinzufügen;

    @Inject
    public EinnahmeBuchen(
            final BuchungssatzHinzufügen buchungssatzHinzufügen) {

        this.buchungssatzHinzufügen = buchungssatzHinzufügen;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount währungsbetrag) {

        // TODO ggf. später prüfen, ob die Kontenarten korrekt sind.
        this.buchungssatzHinzufügen.ausführen(haushaltsbuchId, sollkonto, habenkonto, währungsbetrag);
    }

    public void process(@Observes final EinnahmeBuchenKommando kommando) {
        this.ausführen(kommando.haushaltsbuchId, kommando.sollkonto, kommando.habenkonto, kommando.währungsbetrag);
    }
}
