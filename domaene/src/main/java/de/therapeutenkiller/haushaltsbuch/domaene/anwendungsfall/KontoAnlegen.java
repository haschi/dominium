package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.*;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoMitAnfangsbestandAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeNichtAngelegt;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class KontoAnlegen {
    private final HaushaltsbuchRepository repository;
    private final AnfangsbestandBuchen anfangsbestandBuchen;
    private final Event<KontoWurdeAngelegt> kontoWurdeAngelegtEvent;
    private final Event<KontoWurdeNichtAngelegt> kontoWurdeNichtAngelegt;

    @Inject
    public KontoAnlegen(
            final HaushaltsbuchRepository repository,
            final AnfangsbestandBuchen anfangsbestandBuchen,
            final Event<KontoWurdeAngelegt> kontoWurdeAngelegtEvent,
            final Event<KontoWurdeNichtAngelegt> kontoWurdeNichtAngelegt) {
        this.repository = repository;
        this.anfangsbestandBuchen = anfangsbestandBuchen;
        this.kontoWurdeAngelegtEvent = kontoWurdeAngelegtEvent;
        this.kontoWurdeNichtAngelegt = kontoWurdeNichtAngelegt;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String kontoname,
            final Kontoart kontoart,
            final MonetaryAmount anfangsbestand) {
        this.ausführen(haushaltsbuchId, kontoname, kontoart);
        this.anfangsbestandBuchen.ausführen(haushaltsbuchId, kontoname, anfangsbestand);
    }

    public void ausführen(final UUID haushaltsbuchId, final String kontoname, final Kontoart kontoart) {
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen(haushaltsbuchId);
        final Buchungsregel regel = Buchungsregelfabrik.erzeugen(kontoart);
        final Konto konto = new Konto(kontoname, regel);

        if (haushaltsbuch.istKontoVorhanden(kontoname)) { // NOPMD LoD TODO
            this.kontoWurdeNichtAngelegt.fire(new KontoWurdeNichtAngelegt(haushaltsbuchId, kontoname));
        } else {
            haushaltsbuch.neuesKontoHinzufügen(konto); // NOPMD LoD TODO
            this.kontoWurdeAngelegtEvent.fire(new KontoWurdeAngelegt(haushaltsbuchId, kontoname));
        }
    }

    public void process(@Observes final KontoMitAnfangsbestandAnlegenKommando kommando) {
        this.ausführen(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.kontoart,
                kommando.betrag);
    }

    public void process(@Observes final KontoAnlegenKommando kommando) {
        this.ausführen(kommando.haushaltsbuchId, kommando.kontoname, Kontoart.Aktiv);
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
