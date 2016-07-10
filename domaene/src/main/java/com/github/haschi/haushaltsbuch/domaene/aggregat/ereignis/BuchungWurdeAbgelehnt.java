package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import org.immutables.value.Value;

@Value.Immutable
public interface BuchungWurdeAbgelehnt extends HaushaltsbuchEreignis {

    String grund();
}
