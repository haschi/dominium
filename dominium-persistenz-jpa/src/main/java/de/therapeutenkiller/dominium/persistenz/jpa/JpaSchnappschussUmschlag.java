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
import java.util.UUID;

@Entity
public class JpaSchnappschussUmschlag<A extends Aggregatwurzel<A, UUID, T>, T>
        extends Wertobjekt
        implements Umschlag<Schnappschuss<A, UUID, T>, JpaSchnappschussMetaDaten<UUID>> {

    @EmbeddedId
    private final JpaSchnappschussMetaDaten<UUID> meta;

    @Lob
    private final byte[] snapshot;

    public  JpaSchnappschussUmschlag(
            final UUID streamName,
            final LocalDateTime jetzt,
            final Schnappschuss<A, UUID, T> snapshot) {

        this.meta = new JpaSchnappschussMetaDaten<>(streamName, jetzt);

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
    public final JpaSchnappschussMetaDaten<UUID> getMetaDaten() {
        return this.meta;
    }

    public final Schnappschuss<A, UUID, T> öffnen() {
        try {
            return (Schnappschuss<A, UUID, T>)EventSerializer.deserialize(this.snapshot);
        } catch (final IOException | ClassNotFoundException grund) {
            throw new Serialisierungsfehler(grund);
        }
    }
}
