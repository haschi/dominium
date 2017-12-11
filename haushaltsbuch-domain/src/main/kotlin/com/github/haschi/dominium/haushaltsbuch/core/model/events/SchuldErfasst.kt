package com.github.haschi.dominium.haushaltsbuch.core.model.events

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag

data class SchuldErfasst(
        val position: String,
        val betrag: Währungsbetrag)
