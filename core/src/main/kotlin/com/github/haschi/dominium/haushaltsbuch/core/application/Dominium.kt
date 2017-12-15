package com.github.haschi.dominium.haushaltsbuch.core.application

data class Dominium(
        val haushaltsbuch: HaushaltsbuchführungApi,
        val inventur: InventurApi)