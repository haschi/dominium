package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface JournalWurdeAngelegt
        extends HaushaltsbuchEreignis
{

    UUID aktuelleHaushaltsbuchId();
}
