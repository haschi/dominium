package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeAbgelehnt;

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
        final Konto konto = new Konto(kontoname);

        if (haushaltsbuch.istAnfangsbestandFürKontoVorhanden(konto)) { // NOPMD LoD TODO
            this.buchungWurdeAbgelehntEreignis.fire(new BuchungWurdeAbgelehnt(FEHLERMELDUNG));
        } else {
            this.buchungssatzHinzufügen.ausführen(
                    haushaltsbuchId,
                    konto.ANFANGSBESTAND.getBezeichnung(), // NOPMD LoD TODO
                    kontoname,
                    betrag);
        }
    }

    public void process(@Observes final AnfangsbestandBuchenKommando kommando) {
        this.ausführen(kommando.haushaltsbuch, kommando.kontoname, kommando.währungsbetrag);
    }
}
