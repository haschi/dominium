package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Anweisung("Lege Konto mit Angangsbestand an")
public interface LegeKontoMitAnfangsbestandAn
{

    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String kontoname();

    Kontoart kontoart();

    MonetaryAmount betrag();
}
