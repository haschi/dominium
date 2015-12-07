package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungssatzWurdeErstellt;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    public void ausführen(final UUID haushaltsbuchId, final String von, final String an,
        final MonetaryAmount betrag) {
        this.buchungssatzWurdeErstellt.fire(new BuchungssatzWurdeErstellt(von, an, betrag));
    }
}
