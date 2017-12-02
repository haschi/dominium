package org.github.haschi.haushaltsbuch.api

import org.github.haschi.haushaltsbuch.core.Währungsbetrag

data class SchuldErfasst(
        val position: String,
        val betrag: Währungsbetrag)
