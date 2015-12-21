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
    private final Event<BuchungWurdeNichtAusgeführt> buchungWurdeNichtAusgeführtEvent; // NOPMD Feld zu lang. TODO Regel
    public static final String FEHLERMELDUNG = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";

    @Inject
    public AnfangsbestandBuchen(
            final HaushaltsbuchRepository repository,
            final Event<BuchungWurdeNichtAusgeführt> buchungWurdeNichtAusgeführtEvent) { // NOPMD s.o.
        this.repository = repository;
        this.buchungWurdeNichtAusgeführtEvent = buchungWurdeNichtAusgeführtEvent;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);

        final Konto konto = new Konto(kontoname);
        if (haushaltsbuch.istAnfangsbestandFürKontoVorhanden(konto)) { // NOPMD LoD TODO
            this.buchungWurdeNichtAusgeführtEvent.fire(new BuchungWurdeNichtAusgeführt(FEHLERMELDUNG));
        } else {
            haushaltsbuch.neueBuchungHinzufügen(kontoname, Konto.ANFANGSBESTAND, betrag); // NOPMD LoD TODO
        }
    }
}
