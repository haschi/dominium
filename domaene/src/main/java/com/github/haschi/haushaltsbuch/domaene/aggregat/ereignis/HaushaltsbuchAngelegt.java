package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface HaushaltsbuchAngelegt
        extends HaushaltsbuchEreignis
{
    @Value.Parameter
    UUID id();
}
