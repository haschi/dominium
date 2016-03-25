package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class DieWelt {

    private final HaushaltsbuchMemoryRepository repository;
    private UUID aktuelleHaushaltsbuchId;

    @Inject
    private BeanManager manager;

    @Inject
    public DieWelt(final HaushaltsbuchMemoryRepository repository) {
        this.repository = repository;
    }

    public final List<HaushaltsbuchEreignis> aktuellerEreignisstrom() {
        return this.getStream(this.getAktuelleHaushaltsbuchId());
    }

    public final <T> void kommandoAusführen(final T kommando) {
        this.manager.fireEvent(kommando);
    }

    public final List<HaushaltsbuchEreignis> getStream(final UUID haushaltsbuchId) {
        return this.repository.getStream(haushaltsbuchId);
    }

    public final UUID getAktuelleHaushaltsbuchId() {
        return this.aktuelleHaushaltsbuchId;
    }

    public final void setAktuelleHaushaltsbuchId(final UUID aktuelleHaushaltsbuchId) {
        this.aktuelleHaushaltsbuchId = aktuelleHaushaltsbuchId;
    }
}
