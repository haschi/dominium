package com.github.haschi.haushaltsbuch.api;

import com.github.haschi.modeling.de.Ereignis;

@Ereignis
public interface KontoWurdeAngelegt
{
    String kontobezeichnung();

    Kontoart kontoart();
}
