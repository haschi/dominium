package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

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
    public final Haushaltsbuch besorgen() {
        return this.haushaltsbücher.iterator().next();
    }

    @Override
    public final void leeren() {
        this.haushaltsbücher.clear();
    }
}
