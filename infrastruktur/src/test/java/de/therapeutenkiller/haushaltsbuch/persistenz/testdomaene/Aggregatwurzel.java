package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

public abstract class Aggregatwurzel<A extends Aggregatwurzel<A>> {

    Aggregatwurzel() { }

    protected final void bewirkt(final Domänenereignis<A> ereignis) {
        ereignis.anwendenAuf(this.getSelf());
    }

    protected abstract A getSelf();
}

