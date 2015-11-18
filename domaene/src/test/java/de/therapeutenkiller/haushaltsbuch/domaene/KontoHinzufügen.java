package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.money.MonetaryAmount;

public final class KontoHinzufügen {
    private final HaushaltsbuchRepository repository;

    public KontoHinzufügen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    void ausführen(final MonetaryAmount anfangsbestand, final String kontoname) {

        final Konto konto = new Konto(kontoname, anfangsbestand);
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen();
        haushaltsbuch.neuesKontoHinzufügen(konto, anfangsbestand);
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
