package com.github.haschi.haushaltsbuch.api;

import org.immutables.value.Value;

abstract class Umhüller<T> {
    @Value.Parameter
    public abstract T wert();
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + wert() + ")";
    }
}