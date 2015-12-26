package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class EinnahmeBuchen {

    private final HaushaltsbuchRepository repository;

    @Inject
    public EinnahmeBuchen(final HaushaltsbuchRepository repository) {

        this.repository = repository;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount währungsbetrag) {

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen(haushaltsbuchId);
        haushaltsbuch.neueBuchungHinzufügen(sollkonto, new Konto(habenkonto), währungsbetrag);
    }
}
