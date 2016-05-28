package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.persistenz.jpa.JpaSchnappschuss;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

public final class HaushaltsbuchSchnappschuss extends JpaSchnappschuss {

    private static final long serialVersionUID = -2656820980971196374L;

    public final Version version;
    public ImmutableSet<Konto> konten;
    public ImmutableList<Set<Buchungssatz>> buchungssätze;

    public HaushaltsbuchSchnappschuss(final Version version) {
        super();
        this.version = version;
    }

    @Override
    public Version getVersion() {
        return this.version;
    }
}
