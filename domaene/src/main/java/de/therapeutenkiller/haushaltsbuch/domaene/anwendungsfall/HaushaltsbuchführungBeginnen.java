package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.HaushaltsbuchWurdeAngelegt;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class HaushaltsbuchführungBeginnen {
    private final HaushaltsbuchRepository repository;
    private final Event<HaushaltsbuchWurdeAngelegt> haushaltsbuchWurdeAngelegt;

    @Inject
    public HaushaltsbuchführungBeginnen(
        final HaushaltsbuchRepository repository,
        final Event<HaushaltsbuchWurdeAngelegt> haushaltsbuchWurdeAngelegt) {
        this.repository = repository;
        this.haushaltsbuchWurdeAngelegt = haushaltsbuchWurdeAngelegt;
    }

    public final void ausführen() {
        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch(UUID.randomUUID());
        // this.getRepository().hinzufügen(haushaltsbuch);

        this.getRepository().add(haushaltsbuch);
        // this.haushaltsbuchWurdeAngelegt.fire(new HaushaltsbuchWurdeAngelegt(haushaltsbuch.getIdentität()));
    }

    public final void process(@Observes final HaushaltsbuchführungBeginnenKommando kommando) {
        this.ausführen();
    }

    public final HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
