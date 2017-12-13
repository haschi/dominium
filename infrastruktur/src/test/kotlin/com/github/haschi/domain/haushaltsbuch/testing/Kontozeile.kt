package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter

class Kontozeile {
    @XStreamConverter(BuchungConverter::class)
    var soll: Buchung? = null

    @XStreamConverter(BuchungConverter::class)
    var haben: Buchung? = null
}
