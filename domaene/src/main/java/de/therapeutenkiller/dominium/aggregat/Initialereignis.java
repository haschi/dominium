package de.therapeutenkiller.dominium.aggregat;

public interface Initialereignis<T, A> extends Domänenereignis<A> {
    T getIdentitätsmerkmal();
}
