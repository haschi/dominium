package com.github.haschi.haushaltsbuch;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;

import javax.money.MonetaryAmount;

public class Vermögenswert
{
    String position;

    @XStreamConverter(MoneyConverter.class)
    MonetaryAmount betrag;
}
