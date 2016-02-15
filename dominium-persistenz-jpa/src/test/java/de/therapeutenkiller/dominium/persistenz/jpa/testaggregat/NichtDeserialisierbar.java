package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import java.io.IOException;
import java.io.ObjectInputStream;

public class NichtDeserialisierbar implements Domänenereignis<TestAggregat> {

    private void readObject(final ObjectInputStream input)
            throws IOException, ClassNotFoundException, Throwable {
        throw new Exception();
    }

    @Override
    public void anwendenAuf(final TestAggregat aggregat) { }
}

