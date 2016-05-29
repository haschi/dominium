package com.github.haschi.dominium.testdomäne;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.dominium.modell.Schnappschuss;
import com.github.haschi.dominium.modell.Version;

@ValueObject
public final class TestAggregatSchnappschuss implements Schnappschuss {

    private static final long serialVersionUID = -4081721724050392813L;

    Version version;
    long payload;

    // Da ist ein Fehler im NullReferenzPrüfung Aspekt: Dieser Konstruktor wird geprüft und erzeugt einen Fehler
    // private TestAggregatSchnappschuss() {
    // }

    @Override
    public Version getVersion() {
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

        public TestAggregatSchnappschussBuilder version(final Version version) {
            this.instanz.version = version;
            return this;
        }

        public TestAggregatSchnappschuss build() {
            return this.instanz;
        }

        public TestAggregatSchnappschussBuilder aggregat(final TestAggregat testAggregat) {
            return this
                .version(testAggregat.getAggregatverwalter().getVersion());
        }
    }
}
