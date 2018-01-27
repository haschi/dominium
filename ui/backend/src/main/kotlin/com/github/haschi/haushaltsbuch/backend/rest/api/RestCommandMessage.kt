package com.github.haschi.haushaltsbuch.backend.rest.api

data class RestCommandMessage(
        val type: String,
        val payload: Any,
        val meta: Map<String, String> = emptyMap())
