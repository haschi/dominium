package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat

import de.therapeutenkiller.dominium.modell.Domänenereignis
import groovy.transform.Canonical
import groovy.transform.CompileStatic

@CompileStatic
@Canonical
class NichtSerialisierbar implements Domänenereignis<TestAggregat>, Serializable {

    private void writeObject(final ObjectOutputStream out) throws Exception, IOException {
        throw new Exception();
    }

    @Override
    public void anwendenAuf(final TestAggregat aggregat) {
    }
}
