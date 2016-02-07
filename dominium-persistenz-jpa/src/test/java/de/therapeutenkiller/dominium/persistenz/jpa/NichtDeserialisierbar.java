package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;

import java.io.IOException;

public class NichtDeserialisierbar implements Domänenereignis<TestAggregat> {

    public NichtDeserialisierbar() {
    }

    private void readObject(final java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException, Throwable {
        throw new Exception();
    }

    @Override
    public void anwendenAuf(final TestAggregat aggregat) { }
}
