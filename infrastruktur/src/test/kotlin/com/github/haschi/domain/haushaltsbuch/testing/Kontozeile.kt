package com.github.haschi.domain.haushaltsbuch.testing

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import com.github.haschi.domain.haushaltsbuch.testing.BuchungConverter

class Kontozeile {
    @XStreamConverter(BuchungConverter::class)
    var soll: Buchung? = null

    @XStreamConverter(BuchungConverter::class)
    var haben: Buchung? = null
}