package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeAusgeführt;

import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class EinnahmeBuchen {

    private final HaushaltsbuchRepository repository;
    private final Event<BuchungWurdeAusgeführt> buchungWurdeAusgeführtEreignis;
    private final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEreignis;

    @Inject
    public EinnahmeBuchen(
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
            final MonetaryAmount währungsbetrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);

        if (haushaltsbuch.sindKontenVorhanden(sollkonto, habenkonto)) { // NOPMD LoD TODO
            haushaltsbuch.neueBuchungHinzufügen(sollkonto, new Konto(habenkonto), währungsbetrag);
            this.buchungWurdeAusgeführtEreignis.fire(new BuchungWurdeAusgeführt(sollkonto, habenkonto, währungsbetrag));
        } else {
            final String grund = haushaltsbuch.fehlermeldungFürFehlendeKontenErzeugen(
                    new Konto(sollkonto),
                    new Konto(habenkonto));

            this.buchungWurdeAbgelehntEreignis.fire(new BuchungWurdeAbgelehnt(grund));
        }
    }
}
