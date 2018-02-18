package com.github.haschi.haushaltsbuch.backend.rest.api

data class RestQueryMessage(val type: String,
        val payload: Any,
        val result: String)
