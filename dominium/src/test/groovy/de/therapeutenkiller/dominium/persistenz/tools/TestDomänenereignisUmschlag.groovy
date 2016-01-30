package de.therapeutenkiller.dominium.persistenz.tools

import de.therapeutenkiller.dominium.modell.Domänenereignis
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.persistenz.Umschlag

class TestDomänenereignisUmschlag implements Umschlag<Domänenereignis<TestAggregat>, TestEreignisMetaDaten> {


    private final Domänenereignis<TestAggregat> ereignis
    private final TestEreignisMetaDaten metaDaten

    TestDomänenereignisUmschlag(Domänenereignis<TestAggregat> ereignis, long version, String name) {

        this.ereignis = ereignis
        this.metaDaten = new TestEreignisMetaDaten(version, name);
    }

    @Override
    TestEreignisMetaDaten getMetaDaten() {
        return metaDaten
    }

    @Override
    Domänenereignis<TestAggregat> öffnen() {
        return ereignis
    }
}
