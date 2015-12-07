package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.VermögenWurdeGeändert;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class KontoHinzufügen {
    private final HaushaltsbuchRepository repository;
    private final BuchungssatzHinzufügen buchungssatzHinzufügen;
    private final Event<VermögenWurdeGeändert> vermögenWurdeGeändertEvent;


    @Inject
    public KontoHinzufügen(
        final HaushaltsbuchRepository repository,
        final BuchungssatzHinzufügen buchungssatzHinzufügen,
        final Event<VermögenWurdeGeändert> vermögenWurdeGeändertEvent) {
        this.repository = repository;
        this.buchungssatzHinzufügen = buchungssatzHinzufügen;
        this.vermögenWurdeGeändertEvent = vermögenWurdeGeändertEvent;
    }

    public void ausführen(final UUID haushaltsbuchId, final MonetaryAmount anfangsbestand, final String kontoname) {

        final Konto konto = new Konto(kontoname, anfangsbestand);
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen(haushaltsbuchId);
        haushaltsbuch.neuesKontoHinzufügen(konto, anfangsbestand); // NOPMD LoD TODO
        final MonetaryAmount vermögen = haushaltsbuch.gesamtvermögenBerechnen(); // NOPMD Lod
        this.vermögenWurdeGeändertEvent.fire(new VermögenWurdeGeändert(haushaltsbuchId, vermögen));

        this.buchungssatzHinzufügen.ausführen(
            haushaltsbuchId,
            "Anfangsbestand",
            konto.getBezeichnung(),
            anfangsbestand);
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
