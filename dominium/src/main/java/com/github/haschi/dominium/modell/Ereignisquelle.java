package com.github.haschi.dominium.modell;

public class Ereignisquelle<T> {

    private Version version;

    public Ereignisquelle() {
        this(Version.NEU);
    }

    public Ereignisquelle(Version version) {
        super();
        this.version = version;
    }

    public void bewirkt(final Domänenereignis<T> ereignis) {
        this.version = this.version.nachfolger();
    }

    public Version getVersion() {
        return this.version;
    }
}
