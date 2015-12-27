package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.KontoWurdeNichtAngelegt;

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

    public void ausführen(final UUID haushaltsbuchId, final String kontoname, final MonetaryAmount anfangsbestand) {
        this.ausführen(haushaltsbuchId, kontoname);
        this.anfangsbestandBuchen.ausführen(haushaltsbuchId, kontoname, anfangsbestand);
    }

    public void ausführen(final UUID haushaltsbuchId, final String kontoname) {
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen(haushaltsbuchId);
        final Konto konto = new Konto(kontoname);

        if (haushaltsbuch.istKontoVorhanden(konto)) { // NOPMD LoD TODO
            this.kontoWurdeNichtAngelegt.fire(new KontoWurdeNichtAngelegt(haushaltsbuchId, kontoname));
        } else {
            haushaltsbuch.neuesKontoHinzufügen(konto); // NOPMD LoD TODO
            this.kontoWurdeAngelegtEvent.fire(new KontoWurdeAngelegt(haushaltsbuchId, kontoname));
        }
    }

    public void process(@Observes final KontoAnlegenMitAnfangsbestandKommando kommando) {
        this.ausführen(kommando.haushaltsbuch, kommando.kontoname, kommando.betrag);
    }

    public void process(@Observes final KontoAnlegenKommando kommando) {
        this.ausführen(kommando.haushaltsbuch, kommando.kontoname);
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
