package de.therapeutenkiller.haushaltsbuch.domaene.abfrage;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public class AnfangsbestandBerechnen {
    private final HaushaltsbuchRepository repository;

    @Inject
    public AnfangsbestandBerechnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public final MonetaryAmount ausführen(final UUID haushaltsbuchId) {
        final Haushaltsbuch haushaltsbuch = this.haushaltsbuchBesorgen(haushaltsbuchId);
        return haushaltsbuch.kontostandBerechnen("Anfangsbestand").getBetrag(); // NOPMD LoD TODO
    }

    private Haushaltsbuch haushaltsbuchBesorgen(final UUID haushaltsbuchId) {
        return this.repository.besorgen(haushaltsbuchId);
    }
}
