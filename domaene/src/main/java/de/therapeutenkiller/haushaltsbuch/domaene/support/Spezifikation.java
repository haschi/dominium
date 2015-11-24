package de.therapeutenkiller.haushaltsbuch.domaene.support;

public interface Spezifikation<T> {
    boolean istErfülltVon(T entität);
}
