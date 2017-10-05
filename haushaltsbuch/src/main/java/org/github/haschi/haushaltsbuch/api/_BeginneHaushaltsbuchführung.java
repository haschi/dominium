package org.github.haschi.haushaltsbuch.api;

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Anweisung;

@Anweisung
public interface _BeginneHaushaltsbuchführung
{
    Aggregatkennung id();

    Inventar inventar();
}
