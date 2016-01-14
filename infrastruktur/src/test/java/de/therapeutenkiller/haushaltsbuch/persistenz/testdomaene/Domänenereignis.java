package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

public interface Domänenereignis<A extends Aggregatwurzel<A>> {

    void anwendenAuf(A aggregat);
}
