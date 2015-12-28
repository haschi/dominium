package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAusgeführt;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
final class BuchungssatzHinzufügen {

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

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);

        if (haushaltsbuch.sindKontenVorhanden(sollkonto, habenkonto)) { // NOPMD LoD TODO
            haushaltsbuch.neueBuchungHinzufügen(sollkonto, new Konto(habenkonto), betrag); // NOPMD LoD TODO
            this.buchungWurdeAusgeführtEreignis.fire(new BuchungWurdeAusgeführt(sollkonto, habenkonto, betrag));
        } else {

            final String fehlermeldung = haushaltsbuch.fehlermeldungFürFehlendeKontenErzeugen(
                    new Konto(sollkonto),
                    new Konto(habenkonto));

            this.buchungWurdeAbgelehntEreignis.fire(new BuchungWurdeAbgelehnt(fehlermeldung));
        }
    }

}
