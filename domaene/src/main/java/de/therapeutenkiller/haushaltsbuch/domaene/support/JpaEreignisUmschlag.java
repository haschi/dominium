package de.therapeutenkiller.haushaltsbuch.domaene.support;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class JpaEreignisUmschlag<T> extends Wertobjekt implements EventWrapperSchnittstelle<T> {
    @Id
    private String id = null; // NOPMD Das heißt nun mal so.

    @Lob
    private byte[] ereignis = null; // NOPMD
    private int version = 0; // NOPMD
    private String stream = null; //NOPMD TODO Regel ändern.

    public JpaEreignisUmschlag(final byte[] ereignis, final int version, final String stream) {
        super();

        this.id = String.format("%s(%d)", stream, version);
        this.ereignis = ereignis.clone();
        this.version = version;
        this.stream = stream;
    }

    public JpaEreignisUmschlag() {
        super();
        // Für JPA
    }

    @Override
    public final Domänenereignis<T> getEreignis() {
        return null;
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
