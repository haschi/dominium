package de.therapeutenkiller.dominium.aggregat;

public interface Spezifikation<T> {
    boolean istErfülltVon(T entität);
}
