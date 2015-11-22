package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public class GesamtvermögenBerechnen {
    private final HaushaltsbuchRepository repository;

    @Inject
    public GesamtvermögenBerechnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    public final MonetaryAmount ausführen(final UUID haushaltsbuchId) {
        final Haushaltsbuch haushaltsbuch = this.getRepository().besorgen(haushaltsbuchId);
        return haushaltsbuch.gesamtvermögenBerechnen(); // NOPMD LoD TODO
    }

    public final HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
