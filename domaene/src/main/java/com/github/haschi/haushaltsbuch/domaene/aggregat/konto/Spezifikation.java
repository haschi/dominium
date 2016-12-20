package com.github.haschi.haushaltsbuch.domaene.aggregat.konto;

public interface Spezifikation<T>
{
    boolean istErfülltVon(T entität);
}
