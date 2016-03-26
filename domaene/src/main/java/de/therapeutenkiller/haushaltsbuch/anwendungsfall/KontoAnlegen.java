package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BucheAnfangsbestand;
import de.therapeutenkiller.haushaltsbuch.api.kommando.LegeKontoAn;
import de.therapeutenkiller.haushaltsbuch.api.kommando.LegeKontoMitAnfangsbestandAn;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class KontoAnlegen {

    @Inject
    private HaushaltsbuchRepository repository;

    @Inject
    private Event<BucheAnfangsbestand> anfangsbestandBuchenKommandoEvent;

    public void ausführen(
            @Observes
            final LegeKontoMitAnfangsbestandAn kommando)
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

    public void ausführen(
            @Observes(during = TransactionPhase.BEFORE_COMPLETION)
            final LegeKontoAn kommando)
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
