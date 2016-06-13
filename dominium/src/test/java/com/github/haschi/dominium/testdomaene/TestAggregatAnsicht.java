package com.github.haschi.dominium.testdomaene;

import com.github.haschi.coding.annotation.EventHandler;

public class TestAggregatAnsicht {

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
        return zustand;
    }

    public boolean isBeendet() {
        return beendet;
    }
}
