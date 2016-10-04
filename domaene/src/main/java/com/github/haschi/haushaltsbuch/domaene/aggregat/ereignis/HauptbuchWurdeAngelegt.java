package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface HauptbuchWurdeAngelegt
        extends HaushaltsbuchEreignis
{
    UUID haushaltsbuchId();
}
