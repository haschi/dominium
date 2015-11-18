package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class HaushaltsbuchführungBeginnen implements Kommando {
    private final HaushaltsbuchRepository repository;

    @Inject
    public HaushaltsbuchführungBeginnen(final HaushaltsbuchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void ausführen() {
        this.getRepository().hinzufügen(new Haushaltsbuch());
    }

    public HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
