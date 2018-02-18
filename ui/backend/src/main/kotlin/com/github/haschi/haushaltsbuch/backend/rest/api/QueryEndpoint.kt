package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class QueryEndpoint(val gateway: QueryGateway, val mapper: ObjectMapper)
{

    @RequestMapping("/gateway/query")
    fun post(@RequestBody message: RestQueryMessage)
    {
        val queryType = Class.forName(message.type)
        val resultType = Class.forName(message.result)
        val query = mapper.convertValue(message.payload, queryType)

        gateway.send(query, resultType)
    }
}

