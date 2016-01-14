package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

import java.util.UUID;

public final class TestAggregat extends Aggregatwurzel<TestAggregat, UUID> {

    private boolean aufgerufen = false;

    TestAggregat() {
        super(UUID.randomUUID());
    }

    @Override
    protected TestAggregat getSelf() {
        return this;
    }

    public void falls(final EreignisWurdeGeworfen ereignisWurdeGeworfen) {
        this.aufgerufen = true;
    }

    public void ereignisWerfen() {
        this.bewirkt(new EreignisWurdeGeworfen());
    }

    public boolean ereignisGeworfen() {
        return this.aufgerufen;
    }
}
