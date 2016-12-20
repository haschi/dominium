package com.github.haschi.haushaltsbuch.domaene.aggregat.konto;

interface Spezifikation<T>
{
    boolean istErfülltVon(T entität);
}
