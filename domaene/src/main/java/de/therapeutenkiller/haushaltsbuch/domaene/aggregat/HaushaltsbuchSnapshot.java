package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Snapshot;

import java.util.Set;
import java.util.UUID;

public class HaushaltsbuchSnapshot implements Snapshot<UUID> {
    public final int version;
    private final UUID identität;
    public ImmutableSet<Konto> konten;
    public ImmutableList<Set<Buchungssatz>> buchungssätze;

    public HaushaltsbuchSnapshot(final UUID identität, final int version) {
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
