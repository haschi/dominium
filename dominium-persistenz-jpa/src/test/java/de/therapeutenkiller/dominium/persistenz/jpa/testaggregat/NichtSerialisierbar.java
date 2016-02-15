package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class NichtSerialisierbar implements Domänenereignis<TestAggregat>, Serializable {

    private void writeObject(final ObjectOutputStream out) throws Exception, IOException {
        throw new Exception();
    }

    @Override
    public void anwendenAuf(final TestAggregat aggregat) {
    }
}