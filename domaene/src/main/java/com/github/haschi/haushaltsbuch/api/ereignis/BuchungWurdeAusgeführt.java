package com.github.haschi.haushaltsbuch.api.ereignis;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Buchungssatz;
import com.github.haschi.modeling.de.Ereignis;

@Ereignis
public interface BuchungWurdeAusgeführt
{
    Buchungssatz buchungssatz();
}
