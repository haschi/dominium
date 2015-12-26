package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeNichtAusgeführt;
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
    private final Event<BuchungWurdeNichtAusgeführt> buchungWurdeNichtAusgeführt;

    @Inject
    public BuchungssatzHinzufügen(
            final HaushaltsbuchRepository repository,
            final Event<BuchungssatzWurdeErstellt> buchungssatzWurdeErstellt,
            final Event<BuchungWurdeNichtAusgeführt> buchungWurdeNichtAusgeführt) {

        this.repository = repository;
        this.buchungssatzWurdeErstellt = buchungssatzWurdeErstellt;
        this.buchungWurdeNichtAusgeführt = buchungWurdeNichtAusgeführt;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);

        if (haushaltsbuch.sindKontenVorhanden(sollkonto, habenkonto)) { // NOPMD LoD TODO
            haushaltsbuch.neueBuchungHinzufügen(sollkonto, new Konto(habenkonto), betrag); // NOPMD LoD TODO
            this.buchungssatzWurdeErstellt.fire(new BuchungssatzWurdeErstellt(sollkonto, habenkonto, betrag));
        } else {

            final String fehlermeldung = haushaltsbuch.fehlermeldungFürFehlendeKontenErzeugen(
                    new Konto(sollkonto),
                    new Konto(habenkonto));

            this.buchungWurdeNichtAusgeführt.fire(new BuchungWurdeNichtAusgeführt(fehlermeldung));
        }
    }

}
