package org.github.haschi.haushaltsbuch.api;

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;

import java.util.List;

@Information
public interface _Eröffnungsbilanzkonto
{
    List<Buchung> soll();

    List<Buchung> haben();
}
