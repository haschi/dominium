package de.therapeutenkiller.dominium.persistenz.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.therapeutenkiller.dominium.modell.Domänenereignis;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class DomänenereignisConverter implements AttributeConverter<Domänenereignis, String> {

    @Override
    public String convertToDatabaseColumn(final Domänenereignis domänenereignis) {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(domänenereignis);
        } catch (final JsonProcessingException e) {
            throw new Serialisierungsfehler(e);
        }
    }

    @Override
    public Domänenereignis convertToEntityAttribute(final String s) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(s, Domänenereignis.class);
        } catch (final IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }
}
