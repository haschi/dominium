package com.github.haschi.haushaltsbuch.api.ereignis;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.modeling.de.Ereignis;

@Ereignis
public interface KontoWurdeNichtAngelegt
        extends HaushaltsbuchEreignis
{

    String kontobezeichnung();

    Kontoart kontoart();
}
