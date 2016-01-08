package de.therapeutenkiller.haushaltsbuch.domaene.support;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EventWrapper<T> extends Wertobjekt {
    @Id
    private final String id; // NOPMD Das heißt nun mal so.
    public final Domänenereignis<T> ereignis;
    public final int version;
    public final String stream;

    public EventWrapper(final Domänenereignis<T> ereignis, final int version, final String stream) {
        super();

        this.id = String.format("%s(%d)", stream, version);
        this.ereignis = ereignis;
        this.version = version;
        this.stream = stream;
    }
}
