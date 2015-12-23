package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;

import javax.money.MonetaryAmount;

/**
 * Created by matthias on 23.12.15.
 */
public class Kontostand {

    public String kontoname;

    @XStreamConverter(MoneyConverter.class)
    public MonetaryAmount betrag;

    public Kontoart kontoart;
}
