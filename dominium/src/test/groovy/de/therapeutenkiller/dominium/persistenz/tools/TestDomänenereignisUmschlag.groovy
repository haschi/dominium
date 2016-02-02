package de.therapeutenkiller.dominium.persistenz.tools

import de.therapeutenkiller.dominium.modell.Domänenereignis
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.persistenz.Umschlag

class TestDomänenereignisUmschlag<A> implements Umschlag<Domänenereignis<A>, TestEreignisMetaDaten> {

    private final Domänenereignis<A> ereignis
    private final TestEreignisMetaDaten metaDaten

    TestDomänenereignisUmschlag(Domänenereignis<A> ereignis, long version, String name) {

        this.ereignis = ereignis
        this.metaDaten = new TestEreignisMetaDaten(version, name);
    }

    @Override
    TestEreignisMetaDaten getMetaDaten() {
        return metaDaten
    }

    @Override
    Domänenereignis<A> öffnen() {
        return ereignis
    }
}
