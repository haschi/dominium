package com.github.haschi.dominium.modell;

public final class Aggregatverwalter<T> {

    private Version initialversion;

    static <T> Aggregatverwalter<T> aggregatInitialisieren(final Version version,  final EreignisZiel<T> aggregat) {

        final Aggregatverwalter<T> aggregatverwalter = new Aggregatverwalter<T>();

        final Änderungsverfolgung<T> änderungsverfolgung = new Änderungsverfolgung<>(version);
        aggregatverwalter.setÄnderungsverfolgung(änderungsverfolgung);

        final EreignisQuelle<T> ereignisQuelle = new EreignisQuelle<>();
        aggregatverwalter.setEreignisQuelle(ereignisQuelle);

        aggregatverwalter.getEreignisQuelle().abonnieren(änderungsverfolgung);
        aggregatverwalter.getEreignisQuelle().abonnieren(aggregat);

        aggregatverwalter.setInitialversion(version);

        return aggregatverwalter;
    }

    Version getVersion() {
        return this.getÄnderungsverfolgung().getVersion();
    }

    void bewirkt(final Domänenereignis<T> ereignis) {
        this.getEreignisQuelle().bewirkt(ereignis);
    }

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