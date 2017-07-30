package com.github.haschi.haushaltsbuch.api.refaktorisiert;

import com.github.haschi.modeling.de.Information;

import javax.money.MonetaryAmount;

@Information
public interface Position
{
    String beschreibung();
    MonetaryAmount währungsbetrag();
}
