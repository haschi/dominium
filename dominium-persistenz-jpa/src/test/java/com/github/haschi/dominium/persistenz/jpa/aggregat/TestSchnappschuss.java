package com.github.haschi.dominium.persistenz.jpa.aggregat;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.dominium.modell.Version;

import javax.persistence.Column;
import java.util.UUID;

@ValueObject
public final class TestSchnappschuss extends TestAggregatSchnappschussBasis {

    public static final TestSchnappschuss LEER = builder().get();

    private static final long serialVersionUID = 2104674116259819695L;

    @Column(columnDefinition = "BINARY(16)")
    private UUID identitätsmerkmal;

    private Version version;

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
    public Version getVersion() {
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

        public TestSchnappschussBuilder version(final Version version) {
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