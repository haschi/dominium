package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.money.MonetaryAmount;
import java.util.UUID;

public final class KontoHinzufügen {
    private final HaushaltsbuchRepository repository;
    private final UUID haushaltsbuchId;

    public KontoHinzufügen(final HaushaltsbuchRepository repository, final UUID haushaltsbuchId) {
        this.repository = repository;
        this.haushaltsbuchId = haushaltsbuchId;
    }

    public void ausführen(final MonetaryAmount anfangsbestand, final String kontoname) {

        final Konto konto = new Konto(kontoname, anfangsbestand);
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen(this.haushaltsbuchId);
        haushaltsbuch.neuesKontoHinzufügen(konto, anfangsbestand); // NOPMD LoD TODO
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
