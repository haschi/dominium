package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;

import java.io.IOException;

public class NichtDeserialisierbarWegenFehlenderKlasse implements Domänenereignis<TestAggregat> {

    private void readObject(final java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        throw new ClassNotFoundException();
    }

    @Override
    public void anwendenAuf(final TestAggregat aggregat) { }
}
