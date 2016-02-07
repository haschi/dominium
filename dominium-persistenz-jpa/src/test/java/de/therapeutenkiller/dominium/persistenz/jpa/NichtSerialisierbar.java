package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;

import java.io.IOException;

public class NichtSerialisierbar implements Domänenereignis<TestAggregat> {

    private void writeObject(final java.io.ObjectOutputStream out) throws Exception, IOException {
        throw new Exception();
    }

    @Override
    public void anwendenAuf(final TestAggregat aggregat) {
    }
}
