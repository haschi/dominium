package de.therapeutenkiller.haushaltsbuch.domaene;

public interface Spezifikation<T> {
    boolean istErfülltVon(T entität);
}
