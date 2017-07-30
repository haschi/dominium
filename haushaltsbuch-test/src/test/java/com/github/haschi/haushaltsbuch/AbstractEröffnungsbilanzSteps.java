package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.refaktorisiert.Buchung;

import java.util.List;

public interface AbstractEröffnungsbilanzSteps
{
    public void erstellen();

    public void erstellt(final List<Buchung> buchungen);
}
