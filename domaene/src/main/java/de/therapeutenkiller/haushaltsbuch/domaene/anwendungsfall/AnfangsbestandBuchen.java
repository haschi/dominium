package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AnfangsbestandBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAbgelehnt;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class AnfangsbestandBuchen {
    private final HaushaltsbuchRepository repository;
    private final BuchungssatzHinzufügen buchungssatzHinzufügen;
    private final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEreignis;
    public static final String FEHLERMELDUNG = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";

    @Inject
    public AnfangsbestandBuchen(
            final HaushaltsbuchRepository repository,
            final BuchungssatzHinzufügen buchungssatzHinzufügen,
            final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEreignis) {
        this.repository = repository;
        this.buchungssatzHinzufügen = buchungssatzHinzufügen;
        this.buchungWurdeAbgelehntEreignis = buchungWurdeAbgelehntEreignis;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);

        if (haushaltsbuch.istAnfangsbestandFürKontoVorhanden(kontoname)) { // NOPMD LoD TODO
            this.buchungWurdeAbgelehntEreignis.fire(new BuchungWurdeAbgelehnt(FEHLERMELDUNG));
        } else {
            this.buchungssatzHinzufügen.ausführen(
                    haushaltsbuchId,
                    kontoname,
                    Konto.ANFANGSBESTAND.getBezeichnung(), // NOPMD LoD TODO
                    betrag);
        }
    }

    public void process(@Observes final AnfangsbestandBuchenKommando kommando) {
        this.ausführen(kommando.haushaltsbuchId, kommando.kontoname, kommando.währungsbetrag);
    }
}
