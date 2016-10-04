package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Buchungssatz;
import org.immutables.value.Value;

@Value.Immutable
public interface BuchungWurdeAusgeführt
        extends HaushaltsbuchEreignis
{
    Buchungssatz buchungssatz();
}
