package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class BuchungssatzHinzufügen {

    private final HaushaltsbuchRepository repository;

    @Inject
    public BuchungssatzHinzufügen(
            final HaushaltsbuchRepository repository) {

        this.repository = repository;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.findBy(haushaltsbuchId);

        haushaltsbuch.buchungssatzHinzufügen(sollkonto, habenkonto, betrag);
        this.repository.save(haushaltsbuch);
    }
}
