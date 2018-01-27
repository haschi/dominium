package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class CommandEndpoint(val mapper: ObjectMapper, val gateway: CommandGateway)
{

    @ApiResponses(ApiResponse(code = 202, message = "Accepted"))
    @RequestMapping("/gateway/command", method = [RequestMethod.POST], consumes = ["application/json"])
    fun post(@RequestBody message: RestCommandMessage): ResponseEntity<Any>
    {
        val commandType = Class.forName(message.type)
        val command = mapper.convertValue(message.payload, commandType)

        val result = gateway.sendAndWait<Any>(command)

        println(message)
        println(result)

        return ResponseEntity.accepted().build()
    }

    @RequestMapping("/gateway/command", method = [RequestMethod.GET])
    fun get(): ResponseEntity<Any>
    {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build()
    }
}

