package de.therapeutenkiller.haushaltsbuch.domaene.support;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.IOException;

// TODO Schlüssel des JpaUmschlags sind version + stream;

/**
 * Ein Umschlag für Domänenereignisse zum Speichern in einer
 * Datenbank mit JPA.
 * @param <T> Der Typ des Aggregats, dessen Domänenereignisse gekapselt werden.
 */
@Entity
public class JpaUmschlag<T> extends Wertobjekt implements Umschlag<T> {
    @Id
    private String identitätsmerkmal = null; // NOPMD Das heißt nun mal so.

    @Lob
    private byte[] ereignis = null; // NOPMD
    private int version = 0; // NOPMD
    private String stream = null; //NOPMD TODO Regel ändern.

    public JpaUmschlag(final byte[] ereignis, final int version, final String stream) {
        super();

        this.identitätsmerkmal = String.format("%s(%d)", stream, version);
        this.ereignis = ereignis.clone();
        this.version = version;
        this.stream = stream;
    }

    public JpaUmschlag() {
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
