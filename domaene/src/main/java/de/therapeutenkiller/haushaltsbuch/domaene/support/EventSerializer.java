package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EventSerializer<T> { // NOPMD
    public static <T> byte[] serialize(final Domänenereignis<T> ereignis) throws IOException {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ObjectOutputStream stream = new ObjectOutputStream(outputStream);
        stream.writeObject(ereignis);
        stream.close();
        outputStream.close();

        return outputStream.toByteArray();
    }

    public static <T> T deserialize(final byte[] data) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        final ObjectInputStream stream = new ObjectInputStream(inputStream);
        return (T) stream.readObject();
    }
}