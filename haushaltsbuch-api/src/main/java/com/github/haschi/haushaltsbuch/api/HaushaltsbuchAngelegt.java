package com.github.haschi.haushaltsbuch.api;

import com.github.haschi.modeling.de.Ereignis;
import org.immutables.value.Value;

@Ereignis
public interface HaushaltsbuchAngelegt
{
    @Value.Parameter
    Aggregatkennung id();
}
