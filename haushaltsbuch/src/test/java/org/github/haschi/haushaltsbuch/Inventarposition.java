package org.github.haschi.haushaltsbuch;


import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;
import org.github.haschi.infrastruktur.MoneyConverter;

final class Inventarposition
{
    String gruppe;
    String untergruppe;
    String position;
    @XStreamConverter(MoneyConverter.class)
    Währungsbetrag währungsbetrag;
}
