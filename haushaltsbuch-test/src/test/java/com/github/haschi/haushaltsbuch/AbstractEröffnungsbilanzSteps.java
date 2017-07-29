package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.refaktorisiert.Buchung;

import java.util.List;

public interface AbstractEröffnungsbilanzSteps
{
    public void erstellen(final InventarZustand inventar);

    public void erstellt(final List<Buchung> buchungen);
}
