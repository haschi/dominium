package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest

import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("gateway/command")
class CommandEndpoint
{
    @ApiResponses(ApiResponse(code = 202, message = "Accepted"))
    @RequestMapping(method = [RequestMethod.POST])
    fun post(@RequestBody message: RestCommandMessage): ResponseEntity<Any>
    {
        return ResponseEntity.accepted().build()
    }
}

