package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AnfangsbestandBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class AnfangsbestandBuchen {
    private final HaushaltsbuchRepository repository;
    private final BuchungssatzHinzufügen buchungssatzHinzufügen;

    @Inject
    public AnfangsbestandBuchen(
            final HaushaltsbuchRepository repository,
            final BuchungssatzHinzufügen buchungssatzHinzufügen) {
        this.repository = repository;
        this.buchungssatzHinzufügen = buchungssatzHinzufügen;
    }

    public void ausführen(@Observes final AnfangsbestandBuchenKommando kommando)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        final Haushaltsbuch haushaltsbuch = this.repository.findBy(kommando.haushaltsbuchId);

        haushaltsbuch.anfangsbestandBuchen(
                kommando.kontoname,
                kommando.währungsbetrag
        );

        this.repository.save(haushaltsbuch);
    }
}
