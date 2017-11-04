package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

public class AggregatkennungDeserialisierer extends StdScalarDeserializer<Aggregatkennung>
{
    public AggregatkennungDeserialisierer() {
        super(Aggregatkennung.class);

    }

    public AggregatkennungDeserialisierer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Aggregatkennung deserialize(
            final JsonParser jsonParser,
            final DeserializationContext deserializationContext) throws IOException {

        final String uuid = jsonParser.getText();

        return Aggregatkennung.of(UUID.fromString(uuid));
    }
}