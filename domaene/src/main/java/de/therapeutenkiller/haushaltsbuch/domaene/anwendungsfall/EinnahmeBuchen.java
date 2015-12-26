package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeNichtAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungssatzWurdeErstellt;

import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class EinnahmeBuchen {

    private final HaushaltsbuchRepository repository;
    private final Event<BuchungssatzWurdeErstellt> buchungssatzWurdeErstellt;
    private final Event<BuchungWurdeNichtAusgeführt> buchungWurdeNichtAusgeführ;

    @Inject
    public EinnahmeBuchen(
            final HaushaltsbuchRepository repository,
            final Event<BuchungssatzWurdeErstellt> buchungssatzWurdeErstellt,
            final Event<BuchungWurdeNichtAusgeführt> buchungWurdeNichtAusgeführt) {

        this.repository = repository;
        this.buchungssatzWurdeErstellt = buchungssatzWurdeErstellt;
        this.buchungWurdeNichtAusgeführ = buchungWurdeNichtAusgeführt;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount währungsbetrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);

        if (haushaltsbuch.sindKontenVorhanden(sollkonto, habenkonto)) { // NOPMD LoD TODO
            haushaltsbuch.neueBuchungHinzufügen(sollkonto, new Konto(habenkonto), währungsbetrag);
            this.buchungssatzWurdeErstellt.fire(new BuchungssatzWurdeErstellt(sollkonto, habenkonto, währungsbetrag));
        } else {
            final String grund = haushaltsbuch.fehlermeldungFürFehlendeKontenErzeugen(
                    new Konto(sollkonto),
                    new Konto(habenkonto));

            this.buchungWurdeNichtAusgeführ.fire(new BuchungWurdeNichtAusgeführt(grund));
        }
    }
}
