package org.github.haschi.domain.haushaltsbuch

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import org.github.haschi.domain.haushaltsbuch.modell.core.values.Buchung
import org.github.haschi.infrastruktur.BuchungConverter

class Kontozeile {
    @XStreamConverter(BuchungConverter::class)
    var soll: Buchung? = null

    @XStreamConverter(BuchungConverter::class)
    var haben: Buchung? = null
}
