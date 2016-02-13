package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.IOException;
import java.time.LocalDateTime;

@Entity
public class JpaSchnappschussUmschlag<A extends Aggregatwurzel<A, I>, I> {

    @EmbeddedId
    private final JpaSchnappschussMetaDaten meta;

    @Lob
    private final byte[] snapshot;

    public  JpaSchnappschussUmschlag(
            final String streamName,
            final LocalDateTime jetzt,
            final Schnappschuss<A, I> snapshot) throws IOException {
        this.meta = new JpaSchnappschussMetaDaten(streamName, jetzt);
        this.snapshot = EventSerializer.serialize(snapshot);
    }

    protected JpaSchnappschussUmschlag() {
        this.meta = null;
        this.snapshot = null;
    }
}
