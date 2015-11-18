package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.money.MonetaryAmount;

public final class GesamtvermögenBerechnen {
    private final HaushaltsbuchRepository repository;

    public GesamtvermögenBerechnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    MonetaryAmount ausführen() {
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen();
        return haushaltsbuch.gesamtvermögenBerechnen();
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
