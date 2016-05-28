package com.github.haschi.dominium.modell;

import java.util.HashSet;
import java.util.Set;

public final class EreignisQuelle<T> {

    private final Set<EreignisZiel<T>> abonnenten = new HashSet<>();

    private final Set<T> tabonnenten = new HashSet<>();

    public EreignisQuelle() {
        super();
    }

    public void bewirkt(final Domänenereignis<T> ereignis) {
        this.abonnenten.forEach(ereignis::anwendenAuf);
        this.tabonnenten.forEach(ereignis::anwendenAuf);
    }

    public void abonnieren(final EreignisZiel<T> abonnent) {
        this.abonnenten.add(abonnent);
    }

    public void abonnieren(final T abonnent) {
        this.tabonnenten.add(abonnent);
    }
}
