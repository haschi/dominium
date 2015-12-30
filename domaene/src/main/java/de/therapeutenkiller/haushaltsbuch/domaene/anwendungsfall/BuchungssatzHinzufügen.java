package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class BuchungssatzHinzufügen {

    private final HaushaltsbuchRepository repository;
    private final Event<BuchungWurdeAusgeführt> buchungWurdeAusgeführtEreignis;
    private final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEreignis;

    @Inject
    public BuchungssatzHinzufügen(
            final HaushaltsbuchRepository repository,
            final Event<BuchungWurdeAusgeführt> buchungWurdeAusgeführtEreignis,
            final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEreignis) {

        this.repository = repository;
        this.buchungWurdeAusgeführtEreignis = buchungWurdeAusgeführtEreignis;
        this.buchungWurdeAbgelehntEreignis = buchungWurdeAbgelehntEreignis;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.findBy(haushaltsbuchId);

        haushaltsbuch.buchungssatzHinzufügen(sollkonto, habenkonto, betrag);
        this.repository.save(haushaltsbuch);
    }
}
