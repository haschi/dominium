package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

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
        this.repository.hinzufügen(haushaltsbuch);
    }
}
