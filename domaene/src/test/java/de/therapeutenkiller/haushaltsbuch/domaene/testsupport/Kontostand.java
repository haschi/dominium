package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;

import javax.money.MonetaryAmount;

/**
 * Created by matthias on 23.12.15.
 */
public class Kontostand {

    public String kontoname;
    public @XStreamConverter(MoneyConverter.class) MonetaryAmount betrag;
    public Kontoart kontoart;
}
