package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

public interface Domänenereignis<A> {

    void anwendenAuf(A aggregat);
}
