package com.github.haschi.haushaltsbuch.backend.rest.api

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Version
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class VersionEndpoint(val gateway: CommandGateway)
{
    @RequestMapping("/gateway/version", method = [RequestMethod.GET])
    @ResponseBody
    fun get(): ResponseEntity<Version>
    {
        return ResponseEntity.ok(Version(
                "haushaltsbuch",
                "1.0"))
    }
}