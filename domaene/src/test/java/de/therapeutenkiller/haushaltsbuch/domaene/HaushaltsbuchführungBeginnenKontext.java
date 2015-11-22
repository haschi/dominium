package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HaushaltsbuchführungBeginnenKontext {

    private Haushaltsbuch haushaltsbuch;
    private HaushaltsbuchMemoryRepository repository;

    @Inject
    public HaushaltsbuchführungBeginnenKontext(final HaushaltsbuchMemoryRepository repository) {
        this.repository = repository;
    }

    public final void initialisieren() {
        final Ereignishandler<HaushaltsbuchWurdeAngelegt> angelegt = (event) -> {
            System.out.format("Ereignis Haushaltsbuch wurde angelegt: ID?%s%n",
                event.haushaltsbuch.getHaushaltsbuchId().toString());

            this.haushaltsbuch = event.haushaltsbuch;
        };

        DomainEvents.löschen();
        DomainEvents.registrieren(HaushaltsbuchWurdeAngelegt.class, angelegt);
        this.getRepository().leeren();
    }

    public final Haushaltsbuch getHaushaltsbuch() {
        return this.haushaltsbuch;
    }

    public final HaushaltsbuchMemoryRepository getRepository() {
        return this.repository;
    }
}
