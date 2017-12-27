package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway

data class Dominium(
        val haushaltsbuch: HaushaltsbuchführungApi,
        val inventur: InventurApi,
        val command: CommandGateway,
        val query: QueryGateway,
        val historie: Historie)
{

    fun sendCommand(payload: Any): Any?
    {
        return command.sendAndWait<Any>(payload)
    }
}