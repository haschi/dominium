package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class HaushaltsbuchMemoryRepository implements HaushaltsbuchRepository {

    private final Set<Haushaltsbuch> haushaltsbücher = new HashSet<>();

    @Override
    public void hinzufügen(Haushaltsbuch haushaltsbuch) {
        this.haushaltsbücher.add(haushaltsbuch);
    }

    @Override public Haushaltsbuch besorgen() {
        return this.haushaltsbücher.iterator().next();
    }
}
