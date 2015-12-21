package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeNichtAusgeführt;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class AnfangsbestandBuchen {
    private final HaushaltsbuchRepository repository;
    private final Event<BuchungWurdeNichtAusgeführt> buchungWurdeNichtAusgeführtEvent;

    @Inject
    public AnfangsbestandBuchen(
            final HaushaltsbuchRepository repository,
            final Event<BuchungWurdeNichtAusgeführt> buchungWurdeNichtAusgeführtEvent) {
        this.repository = repository;
        this.buchungWurdeNichtAusgeführtEvent = buchungWurdeNichtAusgeführtEvent;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);

        final Konto konto = new Konto(kontoname);
        if (haushaltsbuch.istAnfangsbestandFürKontoVorhanden(konto)) {
            final String fehlermeldung = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";
            this.buchungWurdeNichtAusgeführtEvent.fire(new BuchungWurdeNichtAusgeführt(fehlermeldung));
        } else {
            haushaltsbuch.neueBuchungHinzufügen(kontoname, Konto.anfangsbestand, betrag); // NOPMD LoD TODO
        }
    }
}
