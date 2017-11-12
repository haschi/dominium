package com.github.haschi.haushaltsbuch.testing;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Ereignis;

@Ereignis
@JsonDeserialize(as = AussereKlasse.class)
public interface _AussereKlasse
{
    KlasseA a();
    KlasseB b();
    KlasseC c();
    KlasseD d();
}
