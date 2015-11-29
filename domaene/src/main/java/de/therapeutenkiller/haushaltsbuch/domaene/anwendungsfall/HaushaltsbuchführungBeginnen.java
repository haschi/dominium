package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.domaene.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchRepository;
import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.support.DomainEvents;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Kommando;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HaushaltsbuchführungBeginnen implements Kommando {
    private final HaushaltsbuchRepository repository;

    @Inject
    public HaushaltsbuchführungBeginnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    @Override
    public final void ausführen() {
        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch();
        this.getRepository().hinzufügen(haushaltsbuch);

        DomainEvents.auslösen(new HaushaltsbuchWurdeAngelegt(haushaltsbuch));
    }

    public final HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
