package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomaene.BearbeitungWurdeBeendet;
import com.github.haschi.dominium.testdomaene.TestAggregat;
import com.github.haschi.dominium.testdomaene.ZustandWurdeGeaendert;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// @SuppressWarnings("all")
public final class TestAggregatProxy extends TestAggregat {

    private final UUID identitätsmerkmal;

    private final Version version;

    private final List<TestAggregatEvent> changes = new ArrayList<>();

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

    public List<TestAggregatEvent> getUncommitedChanges() {
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

    @Override
    public void falls(final BearbeitungWurdeBeendet ereignis) {
        super.falls(ereignis);
        this.changes.add(ImmutableBearbeitungWurdeBeendetMessage.of(ereignis));
    }

    public final void verarbeiten(final ZustandWurdeGeaendertMessage ereignis) {
        super.falls(ereignis.ereignis());
    }

    public final void verarbeiten(final BearbeitungWurdeBeendetMessage ereignis) {
        super.falls(ereignis.ereignis());
    }

    public void wiederherstellen(final List<TestAggregatEvent> ereignisse) {
        ereignisse.forEach(e -> e.anwendenAuf(this));
    }

    @Override
    public boolean equals(final Object anderes) {
        if (this == anderes) {
            return true;
        }

        if (!(anderes instanceof TestAggregatProxy)) {
            return false;
        }

        final TestAggregatProxy that = (TestAggregatProxy) anderes;

        return new EqualsBuilder()
            .append(this.identitätsmerkmal, that.identitätsmerkmal)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.identitätsmerkmal)
            .toHashCode();
    }
}
