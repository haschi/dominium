package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public final class HaushaltsbuchführungBeginnen {
    private final HaushaltsbuchRepository repository;

    @Inject
    public HaushaltsbuchführungBeginnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public void ausführen(@Observes final HaushaltsbuchführungBeginnenKommando kommando)
            throws KonkurrierenderZugriff {
        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch(kommando.identitätsmerkmal);

        haushaltsbuch.neuesKontoHinzufügen(
                Konto.ANFANGSBESTAND.getBezeichnung(),
                Kontoart.Aktiv);

        this.repository.hinzufügen(haushaltsbuch);
    }
}
