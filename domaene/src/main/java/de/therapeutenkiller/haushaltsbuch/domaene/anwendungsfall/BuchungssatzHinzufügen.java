package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungssatzWurdeErstellt;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class BuchungssatzHinzufügen {

    private final HaushaltsbuchRepository repository;
    private final Event<BuchungssatzWurdeErstellt> buchungssatzWurdeErstellt;

    @Inject
    public BuchungssatzHinzufügen(
        final HaushaltsbuchRepository repository,
        final Event<BuchungssatzWurdeErstellt> buchungssatzWurdeErstellt) {

        this.repository = repository;
        this.buchungssatzWurdeErstellt = buchungssatzWurdeErstellt;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);
        haushaltsbuch.neueBuchungHinzufügen(sollkonto, new Konto(habenkonto), betrag); // NOPMD LoD TODO

        this.buchungssatzWurdeErstellt.fire(new BuchungssatzWurdeErstellt(sollkonto, habenkonto, betrag));
    }
}
