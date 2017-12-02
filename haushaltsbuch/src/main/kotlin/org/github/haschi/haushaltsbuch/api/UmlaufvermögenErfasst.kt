package org.github.haschi.haushaltsbuch.api

import org.github.haschi.haushaltsbuch.core.Währungsbetrag

data class UmlaufvermögenErfasst(
        val position: String,
        val betrag: Währungsbetrag)

