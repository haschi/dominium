package org.github.haschi.haushaltsbuch.core.values

data class Eröffnungsbilanzkonto(
        val soll: List<Buchung>,
        val haben: List<Buchung>)

