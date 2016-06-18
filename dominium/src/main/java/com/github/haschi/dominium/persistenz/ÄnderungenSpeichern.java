package com.github.haschi.dominium.persistenz;

import com.github.haschi.dominium.infrastructure.KonkurrierenderZugriff;
import com.github.haschi.dominium.modell.Domänenereignis;

import java.util.List;

public class ÄnderungenSpeichern<I, T> implements AggregatProcessor<I, T> {

    private final Ereignislager<I, T> ereignislager;

    public ÄnderungenSpeichern(final Ereignislager<I, T> ereignislager) {
        super();
        this.ereignislager = ereignislager;
    }

    @Override
    public final void apply(
        final I identitätsmerkmal,
        final List<Domänenereignis<T>> änderungen,
        final long initialversion) {
        try {
            this.ereignislager.ereignisseDemStromHinzufügen(identitätsmerkmal, initialversion, änderungen);
        } catch (final KonkurrierenderZugriff | EreignisstromWurdeNichtGefunden ausnahme) {

            ausnahme.printStackTrace();
        }
    }
}
