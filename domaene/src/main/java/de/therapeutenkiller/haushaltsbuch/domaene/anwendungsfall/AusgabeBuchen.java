package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.api.AusgabeBuchenKommando;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class AusgabeBuchen {

    private final BuchungssatzHinzufügen buchungssatzHinzufügen;

    @Inject
    public AusgabeBuchen(final BuchungssatzHinzufügen buchungssatzHinzufügen) {

        this.buchungssatzHinzufügen = buchungssatzHinzufügen;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {
        this.buchungssatzHinzufügen.ausführen(haushaltsbuchId, sollkonto, habenkonto, betrag);
    }

    public void process(@Observes final AusgabeBuchenKommando kommando) {
        this.ausführen(
                kommando.haushaltsbuch,
                kommando.sollkonto,
                kommando.habenkonto,
                kommando.währungsbetrag
        );
    }
}
