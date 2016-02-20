package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AusgabeBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class AusgabeBuchen {

    private final HaushaltsbuchRepository repository;

    @Inject
    public AusgabeBuchen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public void ausführen(@Observes final AusgabeBuchenKommando kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {
        final Haushaltsbuch haushaltsbuch = this.repository.findBy(kommando.haushaltsbuchId);

        haushaltsbuch.ausgabeBuchen(kommando.sollkonto, kommando.habenkonto, kommando.währungsbetrag);
        this.repository.save(haushaltsbuch);
    }
}
