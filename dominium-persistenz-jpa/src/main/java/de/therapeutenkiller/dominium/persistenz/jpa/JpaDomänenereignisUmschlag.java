package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.dominium.persistenz.Umschlag;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.IOException;
import java.util.UUID;

/**
 * Ein DomänenereignisUmschlag für Domänenereignisse zum Speichern in einer
 * Datenbank mit JPA.
 * @param <A> Der Typ des Aggregats, dessen Domänenereignisse gekapselt werden.
 */
@Entity
public class JpaDomänenereignisUmschlag<A>
        extends Wertobjekt
        implements Umschlag<Domänenereignis<A>, JpaEreignisMetaDaten<UUID>> {

    @EmbeddedId
    private JpaEreignisMetaDaten<UUID> meta = null;

    @Lob
    private byte[] ereignis = null; // NOPMD

    public JpaDomänenereignisUmschlag(
            final Domänenereignis<A> ereignis,
            final JpaEreignisMetaDaten<UUID> meta) {
        super();

        this.meta = meta;

        try {
            this.ereignis = EventSerializer.serialize(ereignis);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Das war nix.", exception);
        }
    }

    public JpaDomänenereignisUmschlag() {
        super();
    }

    public final Domänenereignis<A> getEreignis() {
        try {
            return (Domänenereignis<A>) EventSerializer.deserialize(this.ereignis);
        } catch (final IOException | ClassNotFoundException exception) {
            throw new Serialisierungsfehler(exception);
        }
    }

    @Override
    public final JpaEreignisMetaDaten<UUID> getMetaDaten() {
        return this.meta;
    }

    @Override
    public final Domänenereignis<A> öffnen() {
        return this.getEreignis();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this)
                .append("meta", this.meta)
                .toString();
    }
}
