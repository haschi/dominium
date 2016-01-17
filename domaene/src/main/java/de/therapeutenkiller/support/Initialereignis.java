package de.therapeutenkiller.support;

public interface Initialereignis<T, A> extends Domänenereignis<A> {
    T getIdentitätsmerkmal();
}
