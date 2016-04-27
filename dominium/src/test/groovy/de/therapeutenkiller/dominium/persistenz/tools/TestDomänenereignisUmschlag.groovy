package de.therapeutenkiller.dominium.persistenz.tools

import de.therapeutenkiller.coding.aspekte.ValueObject
import de.therapeutenkiller.dominium.persistenz.Umschlag

@ValueObject
class TestDomänenereignisUmschlag<A> implements Umschlag<A, TestEreignisMetaDaten> {

    private final A ereignis
    private final TestEreignisMetaDaten metaDaten

    TestDomänenereignisUmschlag(A ereignis, long version, String name) {

        this.ereignis = ereignis
        this.metaDaten = new TestEreignisMetaDaten(version, name);
    }

    @Override
    TestEreignisMetaDaten getMetaDaten() {
        return metaDaten
    }

    @Override
    A öffnen() {
        return ereignis
    }
}
