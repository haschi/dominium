package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

/**
 * Created by matthias on 20.12.15.
 */
@Singleton
public final class AnfangsbestandBuchen {
    private final HaushaltsbuchRepository repository;

    @Inject
    public AnfangsbestandBuchen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);
        haushaltsbuch.neueBuchungHinzufügen(kontoname, "Anfangsbestand", betrag); // NOPMD LoD TODO
    }
}
