package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AnfangsbestandBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public final class AnfangsbestandBuchen {
    private final HaushaltsbuchRepository repository;

    @Inject
    public AnfangsbestandBuchen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public void ausführen(@Observes final AnfangsbestandBuchenKommando kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        final Haushaltsbuch haushaltsbuch = this.repository.suchen(kommando.haushaltsbuchId);

        haushaltsbuch.anfangsbestandBuchen(
                kommando.kontoname,
                kommando.währungsbetrag
        );

        this.repository.speichern(haushaltsbuch);
    }
}
