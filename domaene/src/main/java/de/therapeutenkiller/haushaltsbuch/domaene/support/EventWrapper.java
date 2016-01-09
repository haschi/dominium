package de.therapeutenkiller.haushaltsbuch.domaene.support;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class EventWrapper<T> extends Wertobjekt {
    @Id
    private String id = null; // NOPMD Das heißt nun mal so.

    @Lob
    public byte[] ereignis = null; // NOPMD
    public int version = 0; // NOPMD
    public String stream = null; //NOPMD TODO Regel ändern.

    public EventWrapper(final byte[] ereignis, final int version, final String stream) {
        super();

        this.id = String.format("%s(%d)", stream, version);
        this.ereignis = ereignis.clone();
        this.version = version;
        this.stream = stream;
    }

    public EventWrapper() {
        super();
        // Für JPA
    }
}
