package com.github.haschi.dominium.aggregat;

public interface Spezifikation<T> {
    boolean istErfülltVon(T entität);
}
