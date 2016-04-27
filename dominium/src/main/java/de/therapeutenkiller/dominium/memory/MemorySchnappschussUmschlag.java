package de.therapeutenkiller.dominium.memory;

// import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

// @ValueObject
public final class MemorySchnappschussUmschlag<A extends Aggregatwurzel<A, E, I, T>, E extends Domänenereignis<T>, I, T>
        implements Umschlag<Schnappschuss<A, I>, MemorySchnappschussMetaDaten> {

    private final Schnappschuss<A, I> snapshot;
    private final MemorySchnappschussMetaDaten meta;

    public <E> MemorySchnappschussUmschlag(
            final Schnappschuss<A, I> schnappschuss,
            final MemorySchnappschussMetaDaten meta) {
        super();

        this.snapshot = schnappschuss;
        this.meta = meta;
    }

    @Override
    public MemorySchnappschussMetaDaten getMetaDaten() {
        return this.meta;
    }

    @Override
    public Schnappschuss<A, I> öffnen() {
        return this.snapshot;
    }
}
