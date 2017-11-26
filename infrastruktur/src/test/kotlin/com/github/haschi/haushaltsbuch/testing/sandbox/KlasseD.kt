package com.github.haschi.haushaltsbuch.testing.sandbox

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue

data class KlasseD @JsonCreator constructor(@get:JsonIgnore private val d: Double)
{
    @JsonValue
    fun value() = d;
}
