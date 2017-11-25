package org.github.haschi.haushaltsbuch.api

data class Eröffnungsbilanzkonto(
    val soll: List<Buchung>,
    val haben: List<Buchung>)

