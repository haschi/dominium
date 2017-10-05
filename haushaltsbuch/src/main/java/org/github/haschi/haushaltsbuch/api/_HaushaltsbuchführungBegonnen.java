package org.github.haschi.haushaltsbuch.api;

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Ereignis;

@Ereignis
public interface _HaushaltsbuchführungBegonnen
{
    Aggregatkennung id();
}
