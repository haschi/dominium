package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

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
        } catch (final KonkurrierenderZugriff konkurrierenderZugriff) {

            konkurrierenderZugriff.printStackTrace();
        } catch (final EreignisstromWurdeNichtGefunden ereignisstromWurdeNichtGefunden) {
            ereignisstromWurdeNichtGefunden.printStackTrace();
        }
    }
}
