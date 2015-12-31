package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import org.slf4j.Logger;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Singleton
public class HaushaltsbuchAggregatKontext {

    private final HaushaltsbuchMemoryRepository repository;

    @Inject
    private BeanManager manager;

    @Inject
    public HaushaltsbuchAggregatKontext(
            final HaushaltsbuchMemoryRepository repository,
            @SuppressWarnings("CdiInjectionPointsInspection") final Logger log) {
        log.warn("HaushaltsbuchAggregatKontext(%s)", repository.toString());

        this.repository = repository;
    }

    public final void repositoryLeeren() {
        this.repository.leeren();
    }

    public final UUID aktuelleHaushaltsbuchId() {
        return this.repository.getAktuell();
    }

    public final void initialisieren() {
        this.repositoryLeeren();
    }

    public final <T> void kommandoAusführen(final T kommando) {
        this.manager.fireEvent(kommando);
    }

    public final List<Domänenereignis<Haushaltsbuch>> getStream(final UUID haushaltsbuchId) {
        return this.repository.getStream(haushaltsbuchId);
    }
}
