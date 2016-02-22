package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BucheAnfangsbestand;
import de.therapeutenkiller.haushaltsbuch.api.kommando.LegeKontoAn;
import de.therapeutenkiller.haushaltsbuch.api.kommando.LegeKontoMitAnfangsbestandAn;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

public final class KontoAnlegen {
    private final HaushaltsbuchRepository repository;
    private final Event<BucheAnfangsbestand> anfangsbestandBuchenKommandoEvent;

    @Inject
    public KontoAnlegen(
            final HaushaltsbuchRepository repository,
            final Event<BucheAnfangsbestand> anfangsbestandBuchenKommandoEvent) {
        this.repository = repository;
        this.anfangsbestandBuchenKommandoEvent = anfangsbestandBuchenKommandoEvent;
    }

    public void ausführen(@Observes final LegeKontoMitAnfangsbestandAn kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        final LegeKontoAn anlegenKommando = new LegeKontoAn(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.kontoart);

        this.ausführen(anlegenKommando);

        final BucheAnfangsbestand bucheAnfangsbestand = new BucheAnfangsbestand(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.betrag);

        this.anfangsbestandBuchenKommandoEvent.fire(bucheAnfangsbestand);
    }

    public void ausführen(@Observes final LegeKontoAn kommando)
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
