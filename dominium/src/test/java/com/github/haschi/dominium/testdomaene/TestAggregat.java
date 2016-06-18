package com.github.haschi.dominium.testdomaene;

import com.github.haschi.coding.annotation.AggregateIdentifier;
import com.github.haschi.coding.annotation.AggregateRoot;
import com.github.haschi.coding.annotation.EventHandler;
import com.github.haschi.dominium.modell.Memento;

import java.util.UUID;

@AggregateRoot
@SuppressWarnings("all")
public class TestAggregat {

    @AggregateIdentifier
    protected final UUID id;

    private long zustand;

    private boolean bearbeitbar = true;

    public TestAggregat(final UUID identitätsmerkmal) {
        this.id = identitätsmerkmal;
    }

    public void zustandÄndern(final long payload) {
        this.falls(ImmutableZustandWurdeGeaendert.of(payload));
    }

    public void bearbeitungBeenden() {
        if (this.bearbeitbar == false) {
            throw new IllegalStateException("Bearbeitung bereits beendet");
        }

        this.falls(ImmutableBearbeitungWurdeBeendet.of());
    }

    @EventHandler
    protected void falls(final BearbeitungWurdeBeendet bearbeitungWurdeBeendet) {
        this.bearbeitbar = false;
    }

    public void nächsterZustand() {
        this.falls(ImmutableZustandWurdeGeaendert.of(zustand + 1));
    }

    @EventHandler
    public void falls(final ZustandWurdeGeaendert zustandWurdeGeaendert) {
        this.zustand = zustandWurdeGeaendert.payload();
    }

    @Memento
    public abstract static class Snapshot {

        private static final long serialVersionUID = -2648479788904679134L;

        protected abstract long payload();

        public static Snapshot from(final TestAggregat aggregat) {
            return
                ImmutableSnapshot.builder()
                .payload(aggregat.zustand)
                .build();
        }

        public final void restore(final TestAggregat aggregat) {
            aggregat.zustand = this.payload();
        }
    }
}
