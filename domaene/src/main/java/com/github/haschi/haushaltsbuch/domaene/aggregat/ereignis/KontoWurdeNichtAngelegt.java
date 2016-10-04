package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import org.immutables.value.Value;

@Value.Immutable
public interface KontoWurdeNichtAngelegt
        extends HaushaltsbuchEreignis
{

    String kontoname();

    Kontoart kontoart();
}
