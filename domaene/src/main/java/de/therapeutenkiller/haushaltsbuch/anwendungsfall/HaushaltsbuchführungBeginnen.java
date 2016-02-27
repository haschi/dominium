package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class HaushaltsbuchführungBeginnen {

    @Inject
    private HaushaltsbuchRepository repository = null;

    public HaushaltsbuchführungBeginnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public HaushaltsbuchführungBeginnen() {
    }

    public void ausführen(
            @Observes(during = TransactionPhase.BEFORE_COMPLETION)
            final BeginneHaushaltsbuchführung kommando)
            throws KonkurrierenderZugriff {
        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch(kommando.identitätsmerkmal);

        haushaltsbuch.hauptbuchAnlegen();
        haushaltsbuch.journalAnlegen();

        haushaltsbuch.neuesKontoHinzufügen(
                Konto.ANFANGSBESTAND.getBezeichnung(),
                Kontoart.Aktiv);

        this.repository.hinzufügen(haushaltsbuch);
    }
}
