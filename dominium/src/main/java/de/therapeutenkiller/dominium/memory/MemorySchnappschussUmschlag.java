package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

public final class MemorySchnappschussUmschlag<A extends Aggregatwurzel<A, I, T>, I, T>
        extends Wertobjekt
        implements Umschlag<Schnappschuss<A, I, T>, MemorySchnappschussMetaDaten> {

    private final Schnappschuss<A, I, T> snapshot;
    private final MemorySchnappschussMetaDaten meta;

    public <E> MemorySchnappschussUmschlag(
            final Schnappschuss<A, I, T> schnappschuss,
            final MemorySchnappschussMetaDaten meta) {

        this.snapshot = schnappschuss;
        this.meta = meta;
    }

    @Override
    public MemorySchnappschussMetaDaten getMetaDaten() {
        return this.meta;
    }

    @Override
    public Schnappschuss<A, I, T> öffnen() {
        return this.snapshot;
    }
}
