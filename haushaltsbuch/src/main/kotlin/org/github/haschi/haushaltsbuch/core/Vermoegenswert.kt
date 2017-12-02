package org.github.haschi.haushaltsbuch.core

import org.github.haschi.haushaltsbuch.api.Währungsbetrag

data class Vermoegenswert(
        val position: String,
        val währungsbetrag: Währungsbetrag)
