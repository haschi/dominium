package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AggregatkennungSerialisierer extends StdSerializer<Aggregatkennung>
{
    public AggregatkennungSerialisierer() {
        this(null);
    }

    public AggregatkennungSerialisierer(final Class<Aggregatkennung> t) {
        super(t);
    }

    @Override
    public void serialize(
            final Aggregatkennung value,
            final JsonGenerator jgen,
            final SerializerProvider provider)
            throws IOException
    {
        jgen.writeString(value.wert().toString());
    }
}
