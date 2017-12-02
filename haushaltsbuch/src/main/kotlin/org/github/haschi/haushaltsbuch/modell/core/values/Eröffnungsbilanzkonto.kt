package org.github.haschi.haushaltsbuch.modell.core.values

data class Eröffnungsbilanzkonto(
        val soll: List<Buchung>,
        val haben: List<Buchung>)

