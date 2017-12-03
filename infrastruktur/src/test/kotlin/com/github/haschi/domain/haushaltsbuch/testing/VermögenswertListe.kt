package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter

class VermögenswertParameter(
        val position: String,
        @XStreamConverter(MoneyConverter::class)
        val währungsbetrag: Währungsbetrag)
