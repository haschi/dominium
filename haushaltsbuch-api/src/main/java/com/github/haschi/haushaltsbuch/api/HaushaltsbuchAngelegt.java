package com.github.haschi.haushaltsbuch.api;

import com.github.haschi.modeling.de.Ereignis;
import org.immutables.value.Value;

import java.util.UUID;

@Ereignis
public interface HaushaltsbuchAngelegt
{
    @Value.Parameter
    UUID id();
}
