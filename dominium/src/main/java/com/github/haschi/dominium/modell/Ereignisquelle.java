package com.github.haschi.dominium.modell;

import java.util.HashSet;
import java.util.Set;

public class Ereignisquelle<T> {

    private Version version;
    private Set<T> abonnenten = new HashSet<>();

    public Ereignisquelle() {
        this(Version.NEU);
    }

    public Ereignisquelle(Version version) {
        super();
        this.version = version;
    }

    public void bewirkt(final Domänenereignis<T> ereignis) {
        abonnenten.forEach(ereignis::anwendenAuf);
        this.version = this.version.nachfolger();
    }

    public Version getVersion() {
        return this.version;
    }

    public void abonnieren(final T abonnent) {
        this.abonnenten.add(abonnent);
    }
}
