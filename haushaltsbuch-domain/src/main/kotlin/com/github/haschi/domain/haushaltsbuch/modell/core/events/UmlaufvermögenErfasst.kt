package com.github.haschi.domain.haushaltsbuch.modell.core.events

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag

data class UmlaufvermögenErfasst(
        val position: String,
        val betrag: Währungsbetrag)

