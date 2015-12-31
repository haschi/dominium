package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

import javax.money.MonetaryAmount;

public class Kontostand {

    public String kontoname;

    @XStreamConverter(MoneyConverter.class)
    public MonetaryAmount betrag;

    public Kontoart kontoart;
}
