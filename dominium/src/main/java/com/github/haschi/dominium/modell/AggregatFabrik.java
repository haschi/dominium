package com.github.haschi.dominium.modell;

public abstract class AggregatFabrik<A, I> {

    public abstract A erzeugen(final I identitätsmerkmal);
}
