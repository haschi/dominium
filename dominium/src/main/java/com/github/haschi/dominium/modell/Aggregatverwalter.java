package com.github.haschi.dominium.modell;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Aggregatverwalter<T> {

    private Version initialversion;

    static <T> Aggregatverwalter<T> erzeugen(final EreignisZiel<T> aggregat, final Version version) {

        final Aggregatverwalter<T> aggregatverwalter = new Aggregatverwalter<T>();
        aggregatverwalter.initialisieren(aggregat, version, Collections.emptyList());

        return aggregatverwalter;
    }

    public void initialisieren(
            final EreignisZiel<T> aggregat,
            final Version version,
            final List<Domänenereignis<T>> stream) {

        for (final Domänenereignis<T> ereignis : stream) {
            aggregat.falls(ereignis);
        }

        this.änderungsverfolgung = new Änderungsverfolgung<>(version.nachfolger(stream.size()));
        this.ereignisQuelle = new EreignisQuelle<>();

        this.ereignisQuelle.abonnieren(this.änderungsverfolgung);
        this.ereignisQuelle.abonnieren(aggregat);

        this.initialversion = this.änderungsverfolgung.getVersion();
    }

    List<Domänenereignis<T>> getÄnderungen() {
        return this.änderungsverfolgung.alle(ereignis -> ereignis).collect(Collectors.toList());
    }

    Version getVersion() {
        return this.änderungsverfolgung.getVersion();
    }

    void bewirkt(final Domänenereignis<T> ereignis) {
        this.ereignisQuelle.bewirkt(ereignis);
    }

    public Version getInitialversion() {
        return this.initialversion;
    }

    private EreignisQuelle<T> ereignisQuelle;

    private Änderungsverfolgung<T> änderungsverfolgung;

    public Aggregatverwalter() {
        super();
    }
}