package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswert
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter

@XStreamConverter(VermögenswertConverter::class)
class VermögenswertParameter(
        val position: String,
        val währungsbetrag: Währungsbetrag)
