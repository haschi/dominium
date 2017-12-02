package org.github.haschi.haushaltsbuch.modell.core.events

import org.github.haschi.haushaltsbuch.modell.core.values.Währungsbetrag

data class SchuldErfasst(
        val position: String,
        val betrag: Währungsbetrag)
