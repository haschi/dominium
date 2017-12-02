package org.github.haschi.domain.haushaltsbuch


import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import org.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import org.github.haschi.infrastruktur.MoneyConverter

class Inventarposition {
    var gruppe: String? = null
    var untergruppe: String? = null
    var position: String? = null
    @XStreamConverter(MoneyConverter::class)
    var währungsbetrag: Währungsbetrag? = null
}
