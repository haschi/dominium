package de.therapeutenkiller.dominium.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.IOException;

// TODO Schlüssel des JpaUmschlags sind version + stream;

/**
 * Ein DomänenereignisUmschlag für Domänenereignisse zum Speichern in einer
 * Datenbank mit JPA.
 * @param <A> Der Typ des Aggregats, dessen Domänenereignisse gekapselt werden.
 */
@Entity
public class JpaDomänenereignisUmschlag<A>
        extends Wertobjekt
        implements Umschlag<Domänenereignis<A>, JpaEreignisMetaDaten> {

    @Id
    private String identitätsmerkmal = null; // NOPMD Das heißt nun mal so.

    @Lob
    private byte[] ereignis = null; // NOPMD

    @Embedded
    private JpaEreignisMetaDaten meta = null;

    public JpaDomänenereignisUmschlag(final Domänenereignis<A> ereignis, final JpaEreignisMetaDaten meta) {
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
        // Für JPA
    }

    public final String getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    public final Domänenereignis<A> getEreignis() {
        try {
            return EventSerializer.deserialize(this.ereignis);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Geht nicht!", exception);
        } catch (final ClassNotFoundException exception) {
            throw new IllegalArgumentException("Geht nicht.!", exception);
        }
    }

    @Override
    public final JpaEreignisMetaDaten getMetaDaten() {
        return this.meta;
    }

    @Override
    public final Domänenereignis<A> öffnen() {
        return this.getEreignis();
    }
}
