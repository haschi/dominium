package de.therapeutenkiller.dominium.testdomäne;

import de.therapeutenkiller.coding.aspekte.ValueObject;

@ValueObject
public final class ZustandWurdeGeändert extends TestAggregatEreignis {

    private static final long serialVersionUID = 4335272371696585897L;

    private final long payload;

    public ZustandWurdeGeändert(final long payload) {
        super();
        this.payload = payload;
    }

    @Override
    public void anwendenAuf(final TestAggregatEreignisZiel ereignisZiel) {
        ereignisZiel.falls(this);
    }

    public long getPayload() {
        return this.payload;
    }
}
