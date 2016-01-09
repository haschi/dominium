package de.therapeutenkiller.haushaltsbuch.domaene.support;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class EventWrapper<T> extends Wertobjekt {
    @Id
    private String id = null; // NOPMD Das heißt nun mal so.

    @Lob
    public byte[] ereignis = null;
    public int version = 0;
    public String stream = null;

    public EventWrapper(final byte[] ereignis, final int version, final String stream) {
        super();

        this.id = String.format("%s(%d)", stream, version);
        this.ereignis = ereignis;
        this.version = version;
        this.stream = stream;
    }

    public EventWrapper() {
    }
}
