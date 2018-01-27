package com.github.haschi.haushaltsbuch.backend.rest.serialization

data class Testfall<T>
(
        val poko: Any,
        val json: String,
        val schema: String
)