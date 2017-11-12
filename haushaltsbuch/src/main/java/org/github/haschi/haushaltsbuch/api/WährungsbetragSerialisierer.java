package org.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class WährungsbetragSerialisierer extends StdSerializer<Währungsbetrag>
{
    public WährungsbetragSerialisierer() {
        this(null);
    }

    public WährungsbetragSerialisierer(final Class<Währungsbetrag> t) {
        super(t);
    }

    @Override
    public void serialize(
            final Währungsbetrag value,
            final JsonGenerator jgen,
            final SerializerProvider provider)
        throws IOException
    {
        jgen.writeString(value.toString());
    }
}
