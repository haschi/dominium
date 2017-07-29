package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.refaktorisiert.Spalte;
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;

import javax.money.MonetaryAmount;

public class Buchung2
{
    Spalte spalte;
    String buchungstext;

    @XStreamConverter(MoneyConverter.class)
    MonetaryAmount währungsbetrag;
}
