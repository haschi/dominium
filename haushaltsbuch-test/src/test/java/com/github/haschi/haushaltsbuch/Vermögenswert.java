package com.github.haschi.haushaltsbuch;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;

import javax.money.MonetaryAmount;

public class Vermögenswert
{
    public String position;

    @XStreamConverter(MoneyConverter.class)
    public
    MonetaryAmount betrag;
}
