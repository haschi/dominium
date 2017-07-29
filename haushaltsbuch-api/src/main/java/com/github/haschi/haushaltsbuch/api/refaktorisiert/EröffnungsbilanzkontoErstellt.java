package com.github.haschi.haushaltsbuch.api.refaktorisiert;

import com.github.haschi.modeling.de.Ereignis;

import java.util.List;

@Ereignis
public interface EröffnungsbilanzkontoErstellt
{
    List<Buchung> buchungen();
}
