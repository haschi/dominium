package de.therapeutenkiller.dominium.persistenz.jpa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class EventSerializer { // NOPMD

    private EventSerializer() {}

    public static byte[] serialize(final Object ereignis) throws IOException {

        // TODO prüfen, ob der Umgang mit den Streams korrekt ist.
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ObjectOutputStream stream = new ObjectOutputStream(outputStream);
        stream.writeObject(ereignis);
        stream.close();
        outputStream.close();

        return outputStream.toByteArray();
    }

    public static Object deserialize(final byte[] data) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        final ObjectInputStream stream = new ObjectInputStream(inputStream);
        return stream.readObject();
    }
}
