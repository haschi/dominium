package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomaene.BearbeitungWurdeBeendet;
import com.github.haschi.dominium.testdomaene.TestAggregat;
import com.github.haschi.dominium.testdomaene.ZustandWurdeGeaendert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestAggregatProxy extends TestAggregat {

    private final UUID identitätsmerkmal;

    private final Version version;

    private final List<TestAggregatEreignis> changes = new ArrayList<>();

    public TestAggregatProxy(final UUID identitätsmerkmal, final Version version) {
        super(identitätsmerkmal);
        this.identitätsmerkmal = identitätsmerkmal;
        this.version = version;
    }

    public UUID getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    public Version getVersion() {
        return this.version;
    }

    public List<TestAggregatEreignis> getUncommitedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    @Override
    public void falls(final ZustandWurdeGeaendert ereignis) {
        super.falls(ereignis);
        this.changes.add(ImmutableZustandWurdeGeaendertMessage.of(ereignis));
    }

    public final void verarbeiten(final ZustandWurdeGeaendertMessage ereignis) {
        super.falls(ereignis.ereignis());
    }

    @Override
    public void falls(final BearbeitungWurdeBeendet ereignis) {
        super.falls(ereignis);
        this.changes.add(ImmutableBearbeitungWurdeBeendetMessage.of(ereignis));
    }

    public final void verarbeiten(final BearbeitungWurdeBeendetMessage ereignis) {
        super.falls(ereignis.ereignis());
    }

    public void wiederherstellen(final List<TestAggregatEreignis> ereignisse) {
        ereignisse.forEach(e -> e.anwendenAuf(this));
    }
}
