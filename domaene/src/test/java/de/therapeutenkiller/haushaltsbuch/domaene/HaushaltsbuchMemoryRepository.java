package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Singleton
public class HaushaltsbuchMemoryRepository implements HaushaltsbuchRepository {

    private final Set<Haushaltsbuch> haushaltsbücher = new HashSet<>();

    @Override
    public final void hinzufügen(final Haushaltsbuch haushaltsbuch) {
        if (!this.haushaltsbücher.isEmpty()) {
            throw new IllegalStateException();
        }
        this.haushaltsbücher.add(haushaltsbuch);
    }

    @Override
    public final Haushaltsbuch besorgen(final UUID haushaltsbuchId) {
        return this.haushaltsbücher.stream()
            .filter(haushaltsbuch -> haushaltsbuch.getIdentität() == haushaltsbuchId)
            .findFirst()
            .get();
    }

    @Override
    public final void leeren() {
        this.haushaltsbücher.clear();
    }
}
