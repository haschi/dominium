package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Eröffnungsbilanzkonto(
        val soll: List<Buchung>,
        val haben: List<Buchung>)

