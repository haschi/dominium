package org.github.haschi.haushaltsbuch.core.events

import org.github.haschi.haushaltsbuch.core.values.Währungsbetrag

data class UmlaufvermögenErfasst(
        val position: String,
        val betrag: Währungsbetrag)

