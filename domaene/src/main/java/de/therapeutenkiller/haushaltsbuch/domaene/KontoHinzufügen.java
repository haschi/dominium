package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class KontoHinzufügen {
    private final HaushaltsbuchRepository repository;

    @Inject
    public KontoHinzufügen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public void ausführen(final UUID haushaltsbuchId, final MonetaryAmount anfangsbestand, final String kontoname) {

        final Konto konto = new Konto(kontoname, anfangsbestand);
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen(haushaltsbuchId);
        haushaltsbuch.neuesKontoHinzufügen(konto, anfangsbestand); // NOPMD LoD TODO
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
