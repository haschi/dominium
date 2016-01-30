package de.therapeutenkiller.dominium.persistenz.tools

import de.therapeutenkiller.dominium.modell.Domänenereignis
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom
import de.therapeutenkiller.dominium.persistenz.Umschlag
import groovy.transform.CompileStatic

@CompileStatic
class TestEreignisstrom extends Ereignisstrom<TestAggregat, TestEreignisMetaDaten> {
    TestEreignisstrom(String streamName) {
        super(streamName)
    }

    @Override
    protected TestDomänenereignisUmschlag umschlagErzeugen(Domänenereignis<TestAggregat> ereignis) {
        return new TestDomänenereignisUmschlag(ereignis, this.version, this.name);
    }
}