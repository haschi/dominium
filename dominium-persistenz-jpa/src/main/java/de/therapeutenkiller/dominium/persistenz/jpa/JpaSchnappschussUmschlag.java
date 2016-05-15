package de.therapeutenkiller.dominium.persistenz.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import java.io.IOException;

@Entity
@ValueObject
public class JpaSchnappschussUmschlag<S>
        implements Umschlag<S, JpaSchnappschussMetaDaten>  {

    @EmbeddedId
    private final JpaSchnappschussMetaDaten meta;

    @Column
    private String schnappshuss;

    @Column
    @Lob
    private String klasse;

    public  JpaSchnappschussUmschlag(
            final S snapshot,
            final JpaSchnappschussMetaDaten meta) {
        super();

        this.klasse = snapshot.getClass().getCanonicalName();
        this.meta = meta;

        ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            this.schnappshuss = mapper.writeValueAsString(snapshot);
        } catch (final JsonProcessingException e) {
            throw new Serialisierungsfehler(e);
        }
    }

    protected JpaSchnappschussUmschlag() {
        super();
        this.meta = null;
        this.schnappshuss = null;
    }

    @Override
    public final JpaSchnappschussMetaDaten getMetaDaten() {
        return this.meta;
    }

    public final S öffnen() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Class<?> k = Class.forName(this.klasse);
            return (S)mapper.readValue(this.schnappshuss, k);
        } catch (final ClassNotFoundException | IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }
}
