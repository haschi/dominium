package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeAusgeführt;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class AnfangsbestandBuchen {
    private final HaushaltsbuchRepository repository;
    private final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEreignis;
    private final Event<BuchungWurdeAusgeführt> buchungWurdeAusgeführtEreignis;
    public static final String FEHLERMELDUNG = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";

    @Inject
    public AnfangsbestandBuchen(
            final HaushaltsbuchRepository repository,
            final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEreignis,
            final Event<BuchungWurdeAusgeführt> buchungWurdeAusgeführtEreignis) {
        this.repository = repository;
        this.buchungWurdeAbgelehntEreignis = buchungWurdeAbgelehntEreignis;
        this.buchungWurdeAusgeführtEreignis = buchungWurdeAusgeführtEreignis;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);

        final Konto konto = new Konto(kontoname);
        if (haushaltsbuch.istAnfangsbestandFürKontoVorhanden(konto)) { // NOPMD LoD TODO
            this.buchungWurdeAbgelehntEreignis.fire(new BuchungWurdeAbgelehnt(FEHLERMELDUNG));
        } else {
            haushaltsbuch.neueBuchungHinzufügen( // NOPMD LoD TODO
                    Konto.ANFANGSBESTAND.getBezeichnung(), // NOPMD LoD TODO
                    new Konto(kontoname),
                    betrag);

            this.buchungWurdeAusgeführtEreignis.fire(new BuchungWurdeAusgeführt( // NOPMD LoD TODO
                    konto.ANFANGSBESTAND.getBezeichnung(), // NOPMD LoD TODO
                    kontoname,
                    betrag));
        }
    }
}
