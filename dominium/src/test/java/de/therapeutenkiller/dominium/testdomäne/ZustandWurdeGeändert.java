package de.therapeutenkiller.dominium.testdomäne;

public final class ZustandWurdeGeändert extends TestAggregatEreignis {

    private final long payload;

    public ZustandWurdeGeändert(final long payload) {
        super();
        this.payload = payload;
    }

    @Override
    public void anwendenAuf(final TestAggregatEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }

    public long getPayload() {
        return this.payload;
    }
}
