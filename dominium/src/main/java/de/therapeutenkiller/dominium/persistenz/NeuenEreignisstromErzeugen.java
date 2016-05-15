package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import java.util.List;

public class NeuenEreignisstromErzeugen<I, T> implements AggregatProcessor<I, T> {

    private final Ereignislager<I, T> ereignislager;

    public NeuenEreignisstromErzeugen(final Ereignislager<I, T> ereignislager) {
        super();

        this.ereignislager = ereignislager;
    }

    public final void apply(final I identitätsmerkmal, final List<Domänenereignis<T>> änderungen, final long version) {
        this.ereignislager.neuenEreignisstromErzeugen(identitätsmerkmal, änderungen);
    }
}
