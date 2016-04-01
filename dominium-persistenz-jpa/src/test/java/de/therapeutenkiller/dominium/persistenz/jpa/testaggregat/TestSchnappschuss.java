package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import de.therapeutenkiller.dominium.modell.Schnappschuss;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
public final class TestSchnappschuss extends TestAggregatSchnappschussBasis {

    public static final Schnappschuss<TestAggregat, UUID> LEER = builder().get();

    @Column(columnDefinition = "BINARY(16)")
    private UUID identitätsmerkmal;

    private long version;

    private long zustand;

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

    public static TestSchnappschussBuilder builder() {
        return new TestSchnappschussBuilder();
    }

    public static final class TestSchnappschussBuilder {
        private TestSchnappschuss instanz = new TestSchnappschuss();

        public TestSchnappschussBuilder identitätsmerkmal(final UUID uuid) {
            this.instanz.identitätsmerkmal = uuid;
            return this;
        }

        public TestSchnappschussBuilder version(final long version) {
            this.instanz.version = version;
            return this;
        }

        public TestSchnappschussBuilder zustand(final long zustand) {
            this.instanz.zustand = zustand;
            return this;
        }

        public TestSchnappschuss get() {
            return this.instanz;
        }
    }
}