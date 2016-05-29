package com.github.haschi.haushaltsbuch.anwendungsfall;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoAn;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.api.kommando.BucheAnfangsbestand;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoMitAnfangsbestandAn;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

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
        this.repository.speichern(
            haushaltsbuch, haushaltsbuch.getIdentitätsmerkmal(), haushaltsbuch.getAggregatverwalter());
    }

    private HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
