package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.modell.Wertobjekt;

import java.io.Serializable;
import java.util.UUID;

public final class TestSchnappschuss
        extends Wertobjekt
        implements Schnappschuss<TestAggregat, UUID, TestAggregatEreignisziel>, Serializable {

    private long zustand;
    private UUID identitätsmerkmal;
    private long version;

    public TestSchnappschuss() {
    }

    @Override
    public UUID getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    public long getZustand() {
        return this.zustand;
    }

    @Override
    public long getVersion() {
        return this.version;
    }

    @Override
    public TestAggregat wiederherstellen() {
        return new TestAggregat(this);
    }
}