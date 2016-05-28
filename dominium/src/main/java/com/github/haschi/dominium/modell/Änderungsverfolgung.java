package com.github.haschi.dominium.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Änderungsverfolgung<T> implements EreignisZiel<T> {

    private Version version = Version.NEU;
    private List<Domänenereignis<T>> ereignisse = new ArrayList<>();

    public Änderungsverfolgung(final Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return this.version;
    }

    public void falls(final Domänenereignis<T> ereignis) {
        this.version = this.version.nachfolger();
        this.ereignisse.add(ereignis);
    }

    public <R> Stream<R> alle(final Function<? super Domänenereignis<T>, R> function) {
        return this.ereignisse.stream().map(function);
    }
}
