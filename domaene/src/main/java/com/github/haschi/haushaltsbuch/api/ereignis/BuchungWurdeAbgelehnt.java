package com.github.haschi.haushaltsbuch.api.ereignis;

import com.github.haschi.modeling.de.Ereignis;

@Ereignis
public interface BuchungWurdeAbgelehnt
        extends HaushaltsbuchEreignis
{
    String grund();
}
