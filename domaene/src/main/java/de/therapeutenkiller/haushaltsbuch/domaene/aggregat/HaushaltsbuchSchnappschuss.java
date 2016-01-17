package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.therapeutenkiller.dominium.aggregat.Schnappschuss;

import java.util.Set;
import java.util.UUID;

public class HaushaltsbuchSchnappschuss implements Schnappschuss<Haushaltsbuch, UUID> {
    public final long version;
    private final UUID identität;
    public ImmutableSet<Konto> konten;
    public ImmutableList<Set<Buchungssatz>> buchungssätze;

    public HaushaltsbuchSchnappschuss(final UUID identität, final long version) {
        this.identität = identität;
        this.version = version;
    }

    @Override
    public final UUID getIdentitätsmerkmal() {
        return this.identität;
    }

    @Override
    public final long getVersion() {
        return this.version;
    }
}
