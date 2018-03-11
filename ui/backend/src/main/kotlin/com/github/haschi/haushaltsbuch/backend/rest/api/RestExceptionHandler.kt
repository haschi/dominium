package com.github.haschi.haushaltsbuch.backend.rest.api

import com.github.haschi.haushaltsbuch.backend.UnbekanntesKommando
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler()
{
    @ExceptionHandler(value = [
        UnbekanntesKommando::class,
        UnbekannterErgebnisTyp::class,
        FehlerBeiDerDeserialisierung::class])
    fun fehlerVerarbeiten(ex: Exception, request: WebRequest): ResponseEntity<Any>
    {
        return ResponseEntity.badRequest().body(ex.message)
    }
}