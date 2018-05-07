package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Vermoegenswert(
        val kategorie: String,
        val position: String,
        val waehrungsbetrag: Währungsbetrag)
