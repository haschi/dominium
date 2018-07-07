package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag

data class VermögenswertParameter(
        val kategorie: String,
        val position: String,
        val währungsbetrag: Währungsbetrag)
