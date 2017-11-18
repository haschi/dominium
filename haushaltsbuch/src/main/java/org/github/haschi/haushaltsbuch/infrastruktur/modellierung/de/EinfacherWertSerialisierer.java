package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EinfacherWertSerialisierer<T> extends StdSerializer<Wrapper<T>>
{
    public EinfacherWertSerialisierer() {
        this(null);
    }

    public EinfacherWertSerialisierer(final Class<Wrapper<T>> t) {
        super(t);
    }

    @Override
    public void serialize(
            final Wrapper<T> value,
            final JsonGenerator jgen,
            final SerializerProvider provider)
            throws IOException
    {
        jgen.writeObject(value.wert());
    }
}
