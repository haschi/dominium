package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

// @Controller
@EnableAutoConfiguration
class VersionEndpoint
{
    @RequestMapping("/version")
    @ResponseBody
    fun get(): String
    {
        return "1.0";
    }
}