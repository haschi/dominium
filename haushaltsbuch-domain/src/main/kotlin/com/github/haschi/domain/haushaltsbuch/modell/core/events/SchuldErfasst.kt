package com.github.haschi.domain.haushaltsbuch.modell.core.events

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag

data class SchuldErfasst(
        val position: String,
        val betrag: Währungsbetrag)
