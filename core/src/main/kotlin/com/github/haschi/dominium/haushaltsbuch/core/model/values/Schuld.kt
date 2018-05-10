package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Schuld(
        val kategorie: String,
        val position: String,
        val waehrungsbetrag: Währungsbetrag)
