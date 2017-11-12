package org.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;

import java.io.IOException;
import java.util.UUID;

public class WährungsbetragDeserialisierer extends StdScalarDeserializer<Währungsbetrag>
{
    public WährungsbetragDeserialisierer() {
        super(Währungsbetrag.class);

    }

    public WährungsbetragDeserialisierer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Währungsbetrag deserialize(
            final JsonParser jsonParser,
            final DeserializationContext deserializationContext) throws IOException {

        final String betrag = jsonParser.getText();

        return Währungsbetrag.währungsbetrag(betrag);
    }
}