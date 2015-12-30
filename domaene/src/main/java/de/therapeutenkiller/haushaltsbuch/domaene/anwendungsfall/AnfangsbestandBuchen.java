package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.api.kommando.AnfangsbestandBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

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

    public void ausführen(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.findBy(haushaltsbuchId);

        haushaltsbuch.anfangsbestandBuchen(kontoname, betrag, this.buchungssatzHinzufügen);

        this.repository.save(haushaltsbuch);
    }

    public void process(@Observes final AnfangsbestandBuchenKommando kommando) {
        this.ausführen(kommando.haushaltsbuchId, kommando.kontoname, kommando.währungsbetrag);
    }
}
