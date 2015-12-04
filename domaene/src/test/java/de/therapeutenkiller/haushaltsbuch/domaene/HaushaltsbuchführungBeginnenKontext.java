package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.HaushaltsbuchWurdeAngelegt;
import org.slf4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;

@Singleton
public class HaushaltsbuchführungBeginnenKontext {

    private Haushaltsbuch haushaltsbuch;
    private HaushaltsbuchMemoryRepository repository;
    private final HashSet<Haushaltsbuch> haushaltsbücher;

    @Inject
    public HaushaltsbuchführungBeginnenKontext(
        final HaushaltsbuchMemoryRepository repository,
        final Logger log) {
        log.warn("HaushaltsbuchführungBeginnenKontext(%s)", repository.toString());

        this.repository = repository;
        this.haushaltsbücher = new HashSet<>();
    }

    public final void initialisieren() {
        this.haushaltsbücher.clear();
        this.getRepository().leeren();
    }

    public final void haushaltsbuchWurdeAngelegtHandler(@Observes final HaushaltsbuchWurdeAngelegt event) {
        this.haushaltsbuch = event.haushaltsbuch;
    }

    public final Haushaltsbuch getHaushaltsbuch() {
        return this.haushaltsbuch;
    }

    public final HashSet<Haushaltsbuch> getHaushaltsbücher() {
        return this.haushaltsbücher;
    }

    public final <T> T getEvents() {
        throw new NotImplementedException();
    }

    public final HaushaltsbuchMemoryRepository getRepository() {
        return this.repository;
    }
}
