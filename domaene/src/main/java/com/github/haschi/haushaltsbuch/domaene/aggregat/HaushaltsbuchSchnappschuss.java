package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.persistenz.jpa.JpaSchnappschuss;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.UUID;

public final class HaushaltsbuchSchnappschuss extends JpaSchnappschuss {

    private static final long serialVersionUID = -2656820980971196374L;

    public final Version version;
    private final UUID identitätsmerkmal;
    public ImmutableSet<Konto> konten;
    public ImmutableList<Set<Buchungssatz>> buchungssätze;

    public HaushaltsbuchSchnappschuss(final UUID identität, final Version version) {
        super();
        this.identitätsmerkmal = identität;
        this.version = version;
    }

    @Override
    public UUID getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    @Override
    public Version getVersion() {
        return this.version;
    }
}
