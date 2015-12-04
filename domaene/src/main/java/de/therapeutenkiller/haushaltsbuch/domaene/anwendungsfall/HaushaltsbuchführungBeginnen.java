package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.HaushaltsbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Kommando;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HaushaltsbuchführungBeginnen implements Kommando {
    private final HaushaltsbuchRepository repository;
    private final Event<HaushaltsbuchWurdeAngelegt> haushaltsbuchWurdeAngelegt;

    @Inject
    public HaushaltsbuchführungBeginnen(
        final HaushaltsbuchRepository repository,
        final Event<HaushaltsbuchWurdeAngelegt> haushaltsbuchWurdeAngelegt) {
        this.repository = repository;
        this.haushaltsbuchWurdeAngelegt = haushaltsbuchWurdeAngelegt;
    }

    @Override
    public final void ausführen() {
        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch();
        this.getRepository().hinzufügen(haushaltsbuch);

        this.haushaltsbuchWurdeAngelegt.fire(new HaushaltsbuchWurdeAngelegt(haushaltsbuch));
    }

    public final HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
