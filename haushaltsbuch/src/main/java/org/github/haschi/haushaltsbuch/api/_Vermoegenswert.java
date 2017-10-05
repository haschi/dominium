package org.github.haschi.haushaltsbuch.api;

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;

@Information
public interface _Vermoegenswert
{
    String position();

    Währungsbetrag währungsbetrag();
}
