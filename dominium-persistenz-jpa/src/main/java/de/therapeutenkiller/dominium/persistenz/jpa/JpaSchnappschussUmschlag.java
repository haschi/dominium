package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.IOException;
import java.time.LocalDateTime;

@Entity
public class JpaSchnappschussUmschlag<A extends Aggregatwurzel<A, I>, I>
        extends Wertobjekt
        implements Umschlag<Schnappschuss<A, I>, JpaSchnappschussMetaDaten> {

    @EmbeddedId
    private final JpaSchnappschussMetaDaten meta;

    @Lob
    private final byte[] snapshot;

    public  JpaSchnappschussUmschlag(
            final String streamName,
            final LocalDateTime jetzt,
            final Schnappschuss<A, I> snapshot) {

        this.meta = new JpaSchnappschussMetaDaten(streamName, jetzt);

        try {
            this.snapshot = EventSerializer.serialize(snapshot);
        } catch (final IOException grund) {
            throw new Serialisierungsfehler(grund);
        }
    }

    protected JpaSchnappschussUmschlag() {
        this.meta = null;
        this.snapshot = null;
    }

    @Override
    public final JpaSchnappschussMetaDaten getMetaDaten() {
        return this.meta;
    }

    public final Schnappschuss<A, I> öffnen() {
        try {
            return (Schnappschuss<A, I>)EventSerializer.deserialize(this.snapshot);
        } catch (final IOException | ClassNotFoundException grund) {
            throw new Serialisierungsfehler(grund);
        }
    }
}
