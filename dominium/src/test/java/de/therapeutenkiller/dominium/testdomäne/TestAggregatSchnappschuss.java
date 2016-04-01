package de.therapeutenkiller.dominium.testdomäne;

import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.UUID;

public final class TestAggregatSchnappschuss extends Schnappschuss<TestAggregat, UUID> {

    long version;
    long payload;
    UUID identitätsmerkmal;

    // Da ist ein Fehler im NullReferenzPrüfung Aspekt: Dieser Konstruktur wird geprüft und erzeugt einen Fehler
    // private TestAggregatSchnappschuss() {
    // }

    @Override
    public UUID getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    @Override
    public long getVersion() {
        return this.version;
    }

    public long getPayload() {
        return this.payload;
    }

    @Override
    public TestAggregat wiederherstellen() {
        return new TestAggregat(this);
    }

    public static TestAggregatSchnappschussBuilder builder() {
        return new TestAggregatSchnappschussBuilder();
    }

    public static final class TestAggregatSchnappschussBuilder {
        TestAggregatSchnappschuss instanz = new TestAggregatSchnappschuss();

        public TestAggregatSchnappschussBuilder payload(final long payload) {
            this.instanz.payload = payload;
            return this;
        }

        public TestAggregatSchnappschussBuilder version(final long version) {
            this.instanz.version = version;
            return this;
        }

        public TestAggregatSchnappschussBuilder identitätsmerkmal(final UUID identitätsmerkmal) {
            this.instanz.identitätsmerkmal = identitätsmerkmal;
            return this;
        }

        public TestAggregatSchnappschuss build() {
            return this.instanz;
        }
    }
}
