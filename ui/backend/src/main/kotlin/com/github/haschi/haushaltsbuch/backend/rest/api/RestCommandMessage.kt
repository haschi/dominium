package com.github.haschi.haushaltsbuch.backend.rest.api

data class RestCommandMessage(
        val type: String,
        // @com.fasterxml.jackson.annotation.JsonValue
        val payload: Any,
        val meta: Map<String, String> = emptyMap())
