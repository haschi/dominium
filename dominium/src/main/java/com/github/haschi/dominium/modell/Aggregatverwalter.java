package com.github.haschi.dominium.modell;

public final class Aggregatverwalter<T> {

    private Version initialversion;

    public Version getInitialversion() {
        return this.initialversion;
    }

    public void setInitialversion(final Version initialversion) {
        this.initialversion = initialversion;
    }

    private EreignisQuelle<T> ereignisQuelle;

    public EreignisQuelle<T> getEreignisQuelle() {
        return this.ereignisQuelle;
    }

    public void setEreignisQuelle(final EreignisQuelle<T> ereignisQuelle) {
        this.ereignisQuelle = ereignisQuelle;
    }

    private Änderungsverfolgung<T> änderungsverfolgung;

    public Änderungsverfolgung<T> getÄnderungsverfolgung() {
        return this.änderungsverfolgung;
    }

    public void setÄnderungsverfolgung(final Änderungsverfolgung<T> änderungsverfolgung) {
        this.änderungsverfolgung = änderungsverfolgung;
    }

    public Aggregatverwalter() {
        super();
    }
}