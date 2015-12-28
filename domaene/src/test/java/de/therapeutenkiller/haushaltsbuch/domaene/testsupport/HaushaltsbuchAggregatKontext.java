package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.HaushaltsbuchWurdeAngelegt;
import org.slf4j.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Singleton
public class HaushaltsbuchAggregatKontext {

    private UUID haushaltsbuchId;
    private final HaushaltsbuchMemoryRepository repository;
    private final Set<Haushaltsbuch> haushaltsbücher;

    @Inject
    private BeanManager manager;

    @Inject
    public HaushaltsbuchAggregatKontext(
            final HaushaltsbuchMemoryRepository repository,
            @SuppressWarnings("CdiInjectionPointsInspection") final Logger log) {
        log.warn("HaushaltsbuchAggregatKontext(%s)", repository.toString());

        this.repository = repository;
        this.haushaltsbücher = new HashSet<>();
    }

    public final void repositoryLeeren() {
        this.repository.leeren();
    }

    public final UUID aktuelleHaushaltsbuchId() {
        return this.haushaltsbuchId;
    }

    public final Haushaltsbuch aktuellesHaushaltsbuch() {
        return this.repository.besorgen(this.haushaltsbuchId);
    }

    public final void initialisieren() {
        this.haushaltsbücher.clear();
        this.repositoryLeeren();
    }

    public final void haushaltsbuchWurdeAngelegtHandler(@Observes final HaushaltsbuchWurdeAngelegt event) {
        this.haushaltsbuchId = event.haushaltsbuchId;
    }

    public final <T> void kommandoAusführen(final T kommando) {
        this.manager.fireEvent(kommando);
    }
}
