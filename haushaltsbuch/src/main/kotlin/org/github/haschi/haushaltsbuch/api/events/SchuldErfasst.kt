package org.github.haschi.haushaltsbuch.api.events

import org.github.haschi.haushaltsbuch.core.Währungsbetrag

data class SchuldErfasst(
        val position: String,
        val betrag: Währungsbetrag)
