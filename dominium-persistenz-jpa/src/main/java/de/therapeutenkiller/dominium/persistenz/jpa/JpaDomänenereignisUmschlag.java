package de.therapeutenkiller.dominium.persistenz.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.IOException;

/**
 * Ein DomänenereignisUmschlag für Domänenereignisse zum Speichern in einer
 * Datenbank mit JPA.
  */
@Entity
@ValueObject
public class JpaDomänenereignisUmschlag<E>
        implements Umschlag<E, JpaEreignisMetaDaten> {

    @EmbeddedId
    private JpaEreignisMetaDaten meta;

    @Column
    private String ereignis;

    @Column
    private String klasse;

    public JpaDomänenereignisUmschlag(
            final E ereignis,
            final JpaEreignisMetaDaten meta) {
        super();

        this.meta = meta;
        klasse = ereignis.getClass().getCanonicalName();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.ereignis = mapper.writeValueAsString(ereignis);
        } catch (final JsonProcessingException e) {
            throw new Serialisierungsfehler(e);
        }
    }

    public JpaDomänenereignisUmschlag() {
        super();
    }

    @Override
    public final JpaEreignisMetaDaten getMetaDaten() {
        return this.meta;
    }

    @Override
    public final E öffnen() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Class<?> k = Class.forName(this.klasse);
            return (E)mapper.readValue(this.ereignis, k);
        } catch (final ClassNotFoundException | IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }
}
