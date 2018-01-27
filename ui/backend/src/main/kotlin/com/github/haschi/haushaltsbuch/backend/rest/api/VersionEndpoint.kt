package com.github.haschi.haushaltsbuch.backend.rest.api

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class VersionEndpoint(val gateway: CommandGateway)
{
    @RequestMapping("/version", method = [RequestMethod.GET])
    @ResponseBody
    fun get(): String
    {
        return "1.0";
    }
}