package de.therapeutenkiller.haushaltsbuch.domaene.support;

public interface Initialereignis<T, A> extends Domänenereignis<A> {
    T getIdentitätsmerkmal();
}
