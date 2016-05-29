package com.github.haschi.haushaltsbuch.anwendungsfall;

import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import com.github.haschi.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class HaushaltsbuchführungBeginnen {

    @Inject
    private HaushaltsbuchRepository repository;

    public HaushaltsbuchführungBeginnen(final HaushaltsbuchRepository repository) {
        super();
        this.repository = repository;
    }

    public HaushaltsbuchführungBeginnen() {
        super();
    }

    public void ausführen(
            @Observes(during = TransactionPhase.IN_PROGRESS)
            final BeginneHaushaltsbuchführung kommando)
            throws KonkurrierenderZugriff {

        final Haushaltsbuch haushaltsbuch = Haushaltsbuch.erzeugen(kommando.identitätsmerkmal);
        this.repository.hinzufügen(
            haushaltsbuch.getIdentitätsmerkmal(),
            haushaltsbuch.getAggregatverwalter());
    }
}
