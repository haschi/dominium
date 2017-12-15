package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.queryhandling.QueryGateway

data class Dominium(
        val haushaltsbuch: HaushaltsbuchführungApi,
        val inventur: InventurApi,
        val query: QueryGateway,
        val historie: Historie)