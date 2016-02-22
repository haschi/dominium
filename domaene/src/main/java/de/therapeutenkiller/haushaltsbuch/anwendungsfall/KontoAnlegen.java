package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AnfangsbestandBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoMitAnfangsbestandAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

public final class KontoAnlegen {
    private final HaushaltsbuchRepository repository;
    private final Event<AnfangsbestandBuchenKommando> anfangsbestandBuchenKommandoEvent;

    @Inject
    public KontoAnlegen(
            final HaushaltsbuchRepository repository,
            final Event<AnfangsbestandBuchenKommando> anfangsbestandBuchenKommandoEvent) {
        this.repository = repository;
        this.anfangsbestandBuchenKommandoEvent = anfangsbestandBuchenKommandoEvent;
    }

    public void ausführen(@Observes final KontoMitAnfangsbestandAnlegenKommando kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        final KontoAnlegenKommando anlegenKommando = new KontoAnlegenKommando(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.kontoart);

        this.ausführen(anlegenKommando);

        final AnfangsbestandBuchenKommando anfangsbestandBuchenKommando = new AnfangsbestandBuchenKommando(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.betrag);

        this.anfangsbestandBuchenKommandoEvent.fire(anfangsbestandBuchenKommando);
    }

    public void ausführen(@Observes final KontoAnlegenKommando kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {
        final Haushaltsbuch haushaltsbuch = this.getRepository()
                .suchen(kommando.haushaltsbuchId);

        haushaltsbuch.neuesKontoHinzufügen(kommando.kontoname, kommando.kontoart);
        this.repository.speichern(haushaltsbuch);
    }

    private HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
