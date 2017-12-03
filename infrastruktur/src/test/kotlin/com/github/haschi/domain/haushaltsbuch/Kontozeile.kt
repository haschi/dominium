package com.github.haschi.domain.haushaltsbuch

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Buchung
import com.github.haschi.domain.haushaltsbuch.testing.BuchungConverter

class Kontozeile {
    @XStreamConverter(BuchungConverter::class)
    var soll: Buchung? = null

    @XStreamConverter(BuchungConverter::class)
    var haben: Buchung? = null
}
