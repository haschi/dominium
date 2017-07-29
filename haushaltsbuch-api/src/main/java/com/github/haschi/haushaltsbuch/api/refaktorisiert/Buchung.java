package com.github.haschi.haushaltsbuch.api.refaktorisiert;

import com.github.haschi.modeling.de.Information;

import javax.money.MonetaryAmount;

@Information
public interface Buchung
{
    Spalte spalte();
    String text(); // TODO Wertobjekt Buchung
    MonetaryAmount betrag();
}
