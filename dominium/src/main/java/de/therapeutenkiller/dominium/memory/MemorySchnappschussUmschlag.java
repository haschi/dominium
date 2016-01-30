package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

import java.time.LocalDateTime;

public final class MemorySchnappschussUmschlag<A extends Aggregatwurzel<A, I>, I>
        implements Umschlag<Schnappschuss<A, I>, MemorySchnappschussMetaDaten> {

    private final String streamName;
    private final Schnappschuss<A, I> snapshot;
    private final LocalDateTime now;

    public <E> MemorySchnappschussUmschlag(
            final String streamName,
            final Schnappschuss<A, I> snapshot,
            final LocalDateTime now) {

        this.streamName = streamName;
        this.snapshot = snapshot;
        this.now = now;
    }

    @Override
    public MemorySchnappschussMetaDaten getMetaDaten() {
        return new MemorySchnappschussMetaDaten(this.streamName, this.now);
    }

    @Override
    public Schnappschuss<A, I> öffnen() {
        return this.snapshot;
    }
}
