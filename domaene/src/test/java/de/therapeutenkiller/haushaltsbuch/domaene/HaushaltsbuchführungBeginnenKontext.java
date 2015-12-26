package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.HaushaltsbuchWurdeAngelegt;
import org.slf4j.Logger;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Singleton
public class HaushaltsbuchführungBeginnenKontext {

    private UUID haushaltsbuchId;
    private final HaushaltsbuchMemoryRepository repository;
    private final Set<Haushaltsbuch> haushaltsbücher;

    @Inject
    public HaushaltsbuchführungBeginnenKontext(
            final HaushaltsbuchMemoryRepository repository,
            @SuppressWarnings("CdiInjectionPointsInspection") final Logger log) {
        log.warn("HaushaltsbuchführungBeginnenKontext(%s)", repository.toString());

        this.repository = repository;
        this.haushaltsbücher = new HashSet<>();
    }

    public final UUID aktuellesHaushaltsbuch() {
        return this.haushaltsbuchId;
    }

    public final void initialisieren() {
        this.haushaltsbücher.clear();
        this.getRepository().leeren();
    }

    public final void haushaltsbuchWurdeAngelegtHandler(@Observes final HaushaltsbuchWurdeAngelegt event) {
        this.haushaltsbuchId = event.haushaltsbuchId;
    }

    public final Set<Haushaltsbuch> getHaushaltsbücher() {
        return this.haushaltsbücher;
    }

    public final <T> T getEvents() {
        return null;
    }

    public final HaushaltsbuchMemoryRepository getRepository() {
        return this.repository;
    }
}
