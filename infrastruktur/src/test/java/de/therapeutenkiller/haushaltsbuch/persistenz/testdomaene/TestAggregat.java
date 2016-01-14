package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

public final class TestAggregat extends Aggregatwurzel<TestAggregat> {

    private boolean aufgerufen = false;

    TestAggregat() {
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
