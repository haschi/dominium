package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.ArrayList;
import java.util.List;

class EventManager<A> {
    private final List<Domänenereignis<A>> änderungen;

    public EventManager() {
        this.änderungen = new ArrayList<>();
    }

    public final void anwenden(final Domänenereignis<A> ereignis, final A aggregat) {
        ereignis.applyTo(aggregat);
    }

    public final List<Domänenereignis<A>> getÄnderungen() {
        return this.änderungen;
    }

    final void ereignisHinzufügen(final Domänenereignis<A> ereignis) {
        this.änderungen.add(ereignis);
    }
}