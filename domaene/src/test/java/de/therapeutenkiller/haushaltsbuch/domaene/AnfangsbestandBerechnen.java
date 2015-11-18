package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.money.MonetaryAmount;

public final class AnfangsbestandBerechnen {
    private final HaushaltsbuchRepository repository;

    public AnfangsbestandBerechnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    MonetaryAmount ausführen() {
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen();
        final Konto anfangsbestand = haushaltsbuch.kontoSuchen("Anfangsbestand");
        return haushaltsbuch.kontostandBerechnen(anfangsbestand);
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
