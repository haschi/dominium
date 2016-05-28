package com.github.haschi.dominium.modell;

public class Änderungsnachverfolgung<T> {

    private Version version = Version.NEU;

    public Version getVersion() {
        return this.version;
    }

    public void falls(final Domänenereignis<T> ereignis) {
        this.version = this.version.nachfolger();
    }
}
