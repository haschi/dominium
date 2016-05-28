package com.github.haschi.dominium.modell;

import java.util.List;
import java.util.stream.Collectors;

public final class Aggregatverwalter<T> {

    private Version initialversion;

    static <T> Aggregatverwalter<T> aggregatInitialisieren(final Version version,  final EreignisZiel<T> aggregat) {

        final Aggregatverwalter<T> aggregatverwalter = new Aggregatverwalter<T>();
        aggregatverwalter.initialisieren(aggregat, version);

        return aggregatverwalter;
    }

    private static <T> void anwenden(final EreignisZiel<T> aggregat, final List<Domänenereignis<T>> ereignisse) {
        for (final Domänenereignis<T> ereignis : ereignisse) {
            aggregat.falls(ereignis);
        }
    }

    private void initialisieren(
        final EreignisZiel<T> aggregat, final Version version) {
        final Änderungsverfolgung<T> änderungsverfolgung = new Änderungsverfolgung<>(version);
        this.setÄnderungsverfolgung(änderungsverfolgung);

        final EreignisQuelle<T> ereignisQuelle = new EreignisQuelle<>();
        this.setEreignisQuelle(ereignisQuelle);

        this.getEreignisQuelle().abonnieren(änderungsverfolgung);
        this.getEreignisQuelle().abonnieren(aggregat);

        this.setInitialversion(version);
    }

    public void initialisieren(
        final EreignisZiel<T> aggregat, final Version version, final List<Domänenereignis<T>> stream) {
        anwenden(aggregat, stream);

        this.setÄnderungsverfolgung(new Änderungsverfolgung<>(version.nachfolger(stream.size())));
        this.setEreignisQuelle(new EreignisQuelle<>());

        this.getEreignisQuelle().abonnieren(this.getÄnderungsverfolgung());
        this.getEreignisQuelle().abonnieren(aggregat);

        this.setInitialversion(this.getÄnderungsverfolgung().getVersion());
    }

    public void initialisieren(
        final EreignisZiel<T> aggregat, final List<Domänenereignis<T>> stream) {

        anwenden(aggregat, stream);
        this.setÄnderungsverfolgung(new Änderungsverfolgung<>(Version.NEU.nachfolger(stream.size())));
        this.setEreignisQuelle(new EreignisQuelle<>());

        this.getEreignisQuelle().abonnieren(this.getÄnderungsverfolgung());
        this.getEreignisQuelle().abonnieren(aggregat);

        this.setInitialversion(this.getÄnderungsverfolgung().getVersion());
    }

    List<Domänenereignis<T>> getÄnderungen() {
        return this.getÄnderungsverfolgung().alle(ereignis -> ereignis).collect(Collectors.toList());
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

    private void setInitialversion(final Version initialversion) {
        this.initialversion = initialversion;
    }

    private EreignisQuelle<T> ereignisQuelle;

    private EreignisQuelle<T> getEreignisQuelle() {
        return this.ereignisQuelle;
    }

    private void setEreignisQuelle(final EreignisQuelle<T> ereignisQuelle) {
        this.ereignisQuelle = ereignisQuelle;
    }

    private Änderungsverfolgung<T> änderungsverfolgung;

    private Änderungsverfolgung<T> getÄnderungsverfolgung() {
        return this.änderungsverfolgung;
    }

    private void setÄnderungsverfolgung(final Änderungsverfolgung<T> änderungsverfolgung) {
        this.änderungsverfolgung = änderungsverfolgung;
    }

    public Aggregatverwalter() {
        super();
    }
}