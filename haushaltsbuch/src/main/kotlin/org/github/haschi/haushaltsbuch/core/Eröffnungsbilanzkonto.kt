package org.github.haschi.haushaltsbuch.core

import org.github.haschi.haushaltsbuch.core.Buchung

data class Eröffnungsbilanzkonto(
        val soll: List<Buchung>,
        val haben: List<Buchung>)

