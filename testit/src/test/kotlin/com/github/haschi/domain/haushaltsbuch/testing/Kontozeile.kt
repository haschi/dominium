package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung

class Kontozeile(
    val soll: Buchung,
    val haben: Buchung
)
