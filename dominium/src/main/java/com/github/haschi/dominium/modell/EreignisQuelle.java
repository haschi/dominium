package com.github.haschi.dominium.modell;

import java.util.HashSet;
import java.util.Set;

public final class EreignisQuelle<T> {

    private Version version;
    private final Set<T> abonnenten = new HashSet<>();

    public EreignisQuelle() {
        this(Version.NEU);
    }

    public EreignisQuelle(final Version version) {
        super();
        this.version = version;
    }

    public void bewirkt(final Domänenereignis<T> ereignis) {
        this.abonnenten.forEach(ereignis::anwendenAuf);
        this.version = this.version.nachfolger();
    }

    public Version getVersion() {
        return this.version;
    }

    public void abonnieren(final T abonnent) {
        this.abonnenten.add(abonnent);
    }
}
