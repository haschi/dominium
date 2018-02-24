package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class QueryEndpoint(val gateway: QueryGateway, val mapper: ObjectMapper)
{

    @RequestMapping("/gateway/query")
    fun post(@RequestBody message: RestQueryMessage): ResponseEntity<Any>
    {
        val query = Query(message, mapper)

        val ergebnis = gateway.send(query.query, query.resultType).get()

        return ResponseEntity.ok(ergebnis)
    }
}

