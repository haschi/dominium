package de.therapeutenkiller.dominium.aggregat;

public interface Initialereignis<A, T> extends Domänenereignis<A> {
    T getIdentitätsmerkmal();
}
