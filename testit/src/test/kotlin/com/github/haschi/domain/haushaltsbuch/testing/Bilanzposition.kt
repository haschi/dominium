package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag

data class Bilanzposition(
        val seite: String,
        val gruppe: String,
        val posten: String,
        val betrag: Währungsbetrag)