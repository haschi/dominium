package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.money.MonetaryAmount;

public class Buchungssatzdaten {

    public String von;
    public String an;
    @XStreamConverter(MoneyConverter.class)  public MonetaryAmount betrag;
}
