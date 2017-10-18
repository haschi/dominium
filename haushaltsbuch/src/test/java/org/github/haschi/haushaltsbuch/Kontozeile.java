package org.github.haschi.haushaltsbuch;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import org.github.haschi.haushaltsbuch.api.Buchung;
import org.github.haschi.infrastruktur.BuchungConverter;

class Kontozeile
{
    @XStreamConverter(BuchungConverter.class)
    Buchung soll;

    @XStreamConverter(BuchungConverter.class)
    Buchung haben;
}
