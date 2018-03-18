package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class CommandEndpoint(val mapper: ObjectMapper, val gateway: CommandGateway)
{

    @ApiResponses(
            ApiResponse(code = 202, message = "Accepted"),
            ApiResponse(code = 400, message = "Bad Request"))
    @RequestMapping("/gateway/command",
            method = [RequestMethod.POST],
            consumes = ["application/json"])
    fun post(@RequestBody message: RestCommandMessage): ResponseEntity<Any>
    {
        val commandType = try
        {
            Class.forName(message.type)
        }
        catch (ausnahme: ClassNotFoundException)
        {
            return ResponseEntity
                    .badRequest()
                    .body("Unknown command ${message.type}")
        }

        val command = mapper.convertValue(message.payload, commandType)
        gateway.sendAndWait<Any>(command)

        return ResponseEntity.accepted().build()
    }

    @ApiResponses(ApiResponse(code = 418, message = "I am a teapot"))
    @RequestMapping("/gateway/command", method = [RequestMethod.GET])
    fun get(): ResponseEntity<Any>
    {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build()
    }
}

