package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Anweisung
public interface LegeKontoAn
{
    @TargetAggregateIdentifier
    Aggregatkennung haushaltsbuchId();

    String kontobezeichnung();

    Kontoart kontoart();
}
