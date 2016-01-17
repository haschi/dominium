package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.therapeutenkiller.support.Schnappschuss;

import java.util.Set;
import java.util.UUID;

public class HaushaltsbuchSchnappschuss implements Schnappschuss<UUID> {
    public final int version;
    private final UUID identität;
    public ImmutableSet<Konto> konten;
    public ImmutableList<Set<Buchungssatz>> buchungssätze;

    public HaushaltsbuchSchnappschuss(final UUID identität, final int version) {
        this.identität = identität;
        this.version = version;
    }

    @Override
    public final UUID getIdentitätsmerkmal() {
        return this.identität;
    }

    @Override
    public final int getVersion() {
        return this.version;
    }
}
