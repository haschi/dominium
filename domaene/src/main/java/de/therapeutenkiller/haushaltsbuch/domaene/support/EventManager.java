package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.ArrayList;
import java.util.List;

public class EventManager<A> {
    public final List<Domänenereignis<A>> änderungen;

    public EventManager() {
        this.änderungen = new ArrayList<>();
    }

    public final void anwenden(final Domänenereignis<A> ereignis, final A aggregat) {
        ereignis.applyTo(aggregat);
    }

    public final List<Domänenereignis<A>> getÄnderungen() {
        return this.änderungen;
    }

    protected final void ereignisHinzufügen(final Domänenereignis<A> ereignis) {
        this.änderungen.add(ereignis);
    }
}