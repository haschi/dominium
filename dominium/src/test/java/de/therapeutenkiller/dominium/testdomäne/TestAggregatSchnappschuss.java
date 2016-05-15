package de.therapeutenkiller.dominium.testdomäne;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.UUID;

@ValueObject
public final class TestAggregatSchnappschuss implements Schnappschuss<TestAggregat, UUID> {

    private static final long serialVersionUID = -4081721724050392813L;

    long version;
    long payload;
    UUID identitätsmerkmal;

    // Da ist ein Fehler im NullReferenzPrüfung Aspekt: Dieser Konstruktor wird geprüft und erzeugt einen Fehler
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

    public static TestAggregatSchnappschussBuilder builder() {
        return new TestAggregatSchnappschussBuilder();
    }

    public static final class TestAggregatSchnappschussBuilder {
        final TestAggregatSchnappschuss instanz = new TestAggregatSchnappschuss();

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

        public TestAggregatSchnappschussBuilder aggregat(final TestAggregat testAggregat) {
            return this.identitätsmerkmal(testAggregat.getIdentitätsmerkmal())
                .version(testAggregat.getVersion());
        }
    }
}
