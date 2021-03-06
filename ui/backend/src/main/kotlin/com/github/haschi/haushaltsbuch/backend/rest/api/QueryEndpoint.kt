package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class QueryEndpoint(val gateway: QueryGateway, val mapper: ObjectMapper)
{

    @RequestMapping("/gateway/query",
            method = [RequestMethod.POST],
            consumes = ["application/json"])
    fun post(@RequestBody message: RestQueryMessage): ResponseEntity<Any>
    {
        val query = Query(message, mapper)
        val ergebnis = gateway.query(query.query, query.resultType).get()

        return ResponseEntity.ok(ergebnis)
    }
}

