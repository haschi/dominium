package org.github.haschi.haushaltsbuch

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import org.github.haschi.haushaltsbuch.core.values.Buchung
import org.github.haschi.infrastruktur.BuchungConverter

class Kontozeile {
    @XStreamConverter(BuchungConverter::class)
    var soll: Buchung? = null

    @XStreamConverter(BuchungConverter::class)
    var haben: Buchung? = null
}
