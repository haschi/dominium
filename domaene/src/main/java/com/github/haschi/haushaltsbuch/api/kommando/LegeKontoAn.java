package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.util.UUID;

@Anweisung
public interface LegeKontoAn
{

    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String kontoname();

    Kontoart kontoart();
}
