package com.github.haschi.haushaltsbuch.anwendungsfall;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.api.kommando.BucheAnfangsbestand;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class AnfangsbestandBuchen {

    @Inject
    private HaushaltsbuchRepository repository;

    public void ausführen(@Observes final BucheAnfangsbestand kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        final Haushaltsbuch haushaltsbuch = this.repository.suchen(kommando.haushaltsbuchId);

        haushaltsbuch.anfangsbestandBuchen(
                kommando.kontoname,
                kommando.währungsbetrag
        );

        this.repository.speichern(
            haushaltsbuch, haushaltsbuch.getIdentitätsmerkmal(), haushaltsbuch.getAggregatverwalter());
    }
}
