package com.github.haschi.haushaltsbuch.api.refaktorisiert;

import com.github.haschi.modeling.de.Information;

import java.util.List;

@Information
public interface Inventar
{
    List<Position> vermögenswerte();
    List<Position> verbindlichkeiten();
}
