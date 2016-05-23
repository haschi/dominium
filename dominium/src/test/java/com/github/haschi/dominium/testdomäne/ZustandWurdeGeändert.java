package com.github.haschi.dominium.testdomäne;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.dominium.modell.Domänenereignis;

@ValueObject
public final class ZustandWurdeGeändert implements Domänenereignis<TestAggregatEreignisZiel> {

    private static final long serialVersionUID = 5894690972126209956L;

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
