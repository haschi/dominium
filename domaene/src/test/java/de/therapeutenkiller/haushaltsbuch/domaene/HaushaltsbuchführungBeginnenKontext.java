package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.HaushaltsbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.support.DomainEvents;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Ereignishandler;
import org.slf4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class HaushaltsbuchführungBeginnenKontext {

    private Haushaltsbuch haushaltsbuch;
    private HaushaltsbuchMemoryRepository repository;
    private final HashSet<Haushaltsbuch> haushaltsbücher;

    private List<? super Domänenereignis> ereignisse = new LinkedList<>();

    @Inject
    public HaushaltsbuchführungBeginnenKontext(
        final HaushaltsbuchMemoryRepository repository,
        final Logger log) {
        log.warn("HaushaltsbuchführungBeginnenKontext(%s)", repository.toString());

        this.repository = repository;
        this.haushaltsbücher = new HashSet<>();
    }

    public final void initialisieren() {
        this.ereignisse.clear();
        this.haushaltsbücher.clear();

        final Ereignishandler<HaushaltsbuchWurdeAngelegt> angelegt = (event) -> {
            System.out.format("Ereignis Haushaltsbuch wurde angelegt: ID?%s%n",
                event.haushaltsbuch.getIdentität().toString());

            this.haushaltsbuch = event.haushaltsbuch;
            this.ereignisse.add(event);
        };

        DomainEvents.löschen();
        DomainEvents.registrieren(HaushaltsbuchWurdeAngelegt.class, angelegt);
        this.getRepository().leeren();
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
