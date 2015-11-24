package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.support.DomainEvents;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Ereignishandler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;

@Singleton
public class HaushaltsbuchführungBeginnenKontext {

    private Haushaltsbuch haushaltsbuch;
    private HaushaltsbuchMemoryRepository repository;
    private final HashSet<Haushaltsbuch> haushaltsbücher;

    @Inject
    public HaushaltsbuchführungBeginnenKontext(final HaushaltsbuchMemoryRepository repository) {
        this.repository = repository;
        this.haushaltsbücher = new HashSet<>();
    }

    public final void initialisieren() {
        this.haushaltsbücher.clear();

        final Ereignishandler<HaushaltsbuchWurdeAngelegt> angelegt = (event) -> {
            System.out.format("Ereignis Haushaltsbuch wurde angelegt: ID?%s%n",
                event.haushaltsbuch.getIdentität().toString());

            this.haushaltsbuch = event.haushaltsbuch;
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

    public final HaushaltsbuchMemoryRepository getRepository() {
        return this.repository;
    }
}
