package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.support.Domänenereignis;
import de.therapeutenkiller.support.DomänenereignisUmschlag;
import de.therapeutenkiller.support.Wertobjekt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.IOException;

// TODO Schlüssel des JpaUmschlags sind version + stream;

/**
 * Ein DomänenereignisUmschlag für Domänenereignisse zum Speichern in einer
 * Datenbank mit JPA.
 * @param <T> Der Typ des Aggregats, dessen Domänenereignisse gekapselt werden.
 */
@Entity
public class JpaDomänenereignisUmschlag<T> extends Wertobjekt implements DomänenereignisUmschlag<T> {
    @Id
    private String identitätsmerkmal = null; // NOPMD Das heißt nun mal so.

    @Lob
    private byte[] ereignis = null; // NOPMD
    private int version = 0; // NOPMD
    private String stream = null; //NOPMD TODO Regel ändern.

    public JpaDomänenereignisUmschlag(final Domänenereignis<T> ereignis, final int version, final String stream) {
        super();

        this.identitätsmerkmal = String.format("%s(%d)", stream, version);
        this.version = version;
        this.stream = stream;

        try {
            this.ereignis = EventSerializer.serialize(ereignis);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Das war nix.", exception);
        }
    }

    public JpaDomänenereignisUmschlag() {
        super();
        // Für JPA
    }

    @Override
    public final Domänenereignis<T> getEreignis() {
        try {
            return EventSerializer.deserialize(this.ereignis);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Geht nicht!", exception);
        } catch (final ClassNotFoundException exception) {
            throw new IllegalArgumentException("Geht nicht.!", exception);
        }
    }

    @Override
    public final int getVersion() {
        return this.version;
    }

    @Override
    public final String getStreamName() {
        return this.stream;
    }
}
