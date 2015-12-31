package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeNichtAngelegt;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AnfangsbestandBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoMitAnfangsbestandAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

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

    public void ausführen(@Observes final KontoMitAnfangsbestandAnlegenKommando kommando) {

        final KontoAnlegenKommando anlegenKommando = new KontoAnlegenKommando(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.kontoart);

        this.ausführen(anlegenKommando);

        final AnfangsbestandBuchenKommando anfangsbestandBuchenKommando = new AnfangsbestandBuchenKommando(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.betrag);

        this.anfangsbestandBuchen.ausführen(anfangsbestandBuchenKommando);
    }

    public void ausführen(@Observes final KontoAnlegenKommando kommando) {
        final Haushaltsbuch haushaltsbuch = this.getRepository().findBy(kommando.haushaltsbuchId);

        haushaltsbuch.neuesKontoHinzufügen(kommando.kontoname, kommando.kontoart);
        this.repository.save(haushaltsbuch);
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
