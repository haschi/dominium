package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;

@Anweisung("Lege Konto mit Anfangs&shy;bestand an")
public interface LegeKontoMitAnfangsbestandAn
{
    @TargetAggregateIdentifier
    Aggregatkennung haushaltsbuchId();

    String kontobezeichnung();

    Kontoart kontoart();

    MonetaryAmount betrag();
}
