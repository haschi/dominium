package org.github.haschi.haushaltsbuch.core

import org.github.haschi.haushaltsbuch.api.Währungsbetrag

data class Schuld(
        val position: String,
        val währungsbetrag: Währungsbetrag)
