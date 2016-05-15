package de.therapeutenkiller.dominium.persistenz.jpa.aggregat;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@ValueObject
public final class TestSchnappschuss extends TestAggregatSchnappschussBasis {

    public static final TestSchnappschuss LEER = builder().get();

    @Column(columnDefinition = "BINARY(16)")
    private UUID identitätsmerkmal;

    private long version;

    private long zustand;

    public TestSchnappschuss() {
        super();
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

    public static TestSchnappschussBuilder builder() {
        return new TestSchnappschussBuilder();
    }

    public static final class TestSchnappschussBuilder {
        private final TestSchnappschuss instanz = new TestSchnappschuss();

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