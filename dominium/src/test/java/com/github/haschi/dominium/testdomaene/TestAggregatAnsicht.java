package com.github.haschi.dominium.testdomaene;

import com.github.haschi.coding.annotation.EventHandler;

public final class TestAggregatAnsicht {

    private long zustand;
    private boolean beendet;

    @EventHandler
    public void falls(final ZustandWurdeGeaendert ereignis) {
        this.zustand = ereignis.payload();
    }

    @EventHandler
    public void falls(final BearbeitungWurdeBeendet ereignis) {
        this.beendet = true;
    }

    public long getZustand() {
        return this.zustand;
    }

    public boolean isBeendet() {
        return this.beendet;
    }
}
