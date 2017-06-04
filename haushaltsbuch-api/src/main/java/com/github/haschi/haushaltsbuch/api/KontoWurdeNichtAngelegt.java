package com.github.haschi.haushaltsbuch.api;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.modeling.de.Ereignis;

@Ereignis
public interface KontoWurdeNichtAngelegt
{
    String kontobezeichnung();

    Kontoart kontoart();
}
