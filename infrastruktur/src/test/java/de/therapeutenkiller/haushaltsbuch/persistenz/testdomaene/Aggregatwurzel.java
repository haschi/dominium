package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Entität;

public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, T>, T> extends Entität<T> {

    protected Aggregatwurzel(final T identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    protected final void bewirkt(final Domänenereignis<A> ereignis) {
        ereignis.anwendenAuf(this.getSelf());
    }

    protected abstract A getSelf();
}

