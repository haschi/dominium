package de.therapeutenkiller.haushaltsbuch.domaene.support;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;

import java.util.Set;
import java.util.UUID;

/**
 * Created by matthias on 30.12.15.
 */
public class Haushaltsbuchsnapshot implements Snapshot<UUID> {
    public int version;
    public UUID identität;
    public ImmutableSet<Konto> konten;
    public ImmutableList<Set<Buchungssatz>> buchungssätze;

    public Haushaltsbuchsnapshot(final UUID identität, final int version) {
        this.identität = identität;
        this.version = version;
    }

    @Override
    public final UUID getIdentifier() {
        return this.identität;
    }

    @Override
    public final int getVersion() {
        return this.version;
    }
}
