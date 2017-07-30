package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableInventar;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Inventar;

public class AktuellesInventar
{
    private final ImmutableInventar inventar;

    public AktuellesInventar(final ImmutableInventar inventar)
    {

        this.inventar = inventar;
    }

    public Inventar get()
    {
        return inventar;
    }
}
